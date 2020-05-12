<?php


namespace app\client\controller\v1;
use app\common\model\Contacts as ContactsModel;
use app\common\model\User as UserModel;
use app\common\model\UserBranch;
use app\common\model\UserState;
use app\common\service\Token as TokenService;
use app\common\validate\ContactsMustBePositiveInt;
use app\lib\exception\ContactsException;
use app\lib\exception\TaskException;
use app\lib\exception\UserException;

class Contacts
{
    const KINDS_ARR = [
        "train" => 1,
        "introduce" => 2
    ];

    const TASKS_ARR = [
        "train" => '9',
        'introduce' => '16'
    ];

    const FIELD_ARR = [
        'train' => [
            "develop_contact_first",
            "develop_contact_second"
        ],
        'introduce' => [
            "recommend_first",
            "recommend_second"
        ]
    ];

    /**
     * Notes:获取所有培养联系人
     * User: hua-bang
     * Date: 2020/5/11
     * Time: 15:48
     * @return \think\response\Json
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function getTrainAll(){
        $contactList = ContactsModel::where(['status'=>1,'kind'=>self::KINDS_ARR['train']])->select();
        return json($contactList);
    }

    public function getIntroduceAll(){
        $contactList = ContactsModel::where(['status'=>1,'kind'=>self::KINDS_ARR['introduce']])->select();
        return json($contactList);
    }


    public function editContacts($kind){
        (new ContactsMustBePositiveInt())->goCheck($kind);
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $taskIdFromSql = UserState::where('casid',$casId)->value("task_id");
        if($taskIdFromSql!=self::TASKS_ARR[$kind]){
            throw new TaskException(['msg'=>'任务不属于用户当前阶段']);
        }
        if(!$casId){
            throw new UserException(['msg' => "用户不存在呀."]);
        }
        $userBranch = UserBranch::where('id',$casId)->find();
        if(!$userBranch){
            throw new UserException(['msg' => "该用户信息还没录入，请联系管理员."]);
        }
        $data = [];
        foreach (self::FIELD_ARR[$kind] as $key){
            $data[$key] = input("post.${key}");
        }
        if($data[self::FIELD_ARR[$kind][0]]==$data[self::FIELD_ARR[$kind][1]])
            throw new ContactsException(['msg'=>"两个联系人不能相同"]);
        $count = ContactsModel::where('id','in',$data)->where('kind',self::KINDS_ARR[$kind])->count();
        if($count!=2){
            throw new ContactsException(['msg'=>"其中某位联系人不存在，请正确选择好联系人"]);
        }
        foreach ($data as $k => $v){
            $userBranch[$k] = $v;
        }
        $res = $userBranch->save();
        $code = $res?200:500;
        $msg = $res?"提交成功":"提交失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }
}