<?php


namespace app\party\controller\v1;


use app\common\model\UserBranch;
use app\lib\exception\AdminException;
use app\lib\exception\ContactsException;
use app\lib\exception\ParameterException;
use app\lib\exception\SuccessData;
use app\lib\exception\SuccessMessage;
use think\Exception;

class Contacts
{
    /**
     * Notes:获取联系人列表【含分页】
     * User: charl
     * Date: 2020/8/2
     * Time: 15:29
     * @param int $limit
     * @throws SuccessData
     * @throws \think\db\exception\DbException
     */
    public function index($limit = 10)
    {
        $contacts = \app\common\model\Contacts::where(['status'=>1])->paginate($limit);
        throw new SuccessData(['data'=>$contacts]);
    }

    /**
     * Notes:指定培养联系人
     * User: charl
     * Date: 2020/8/2
     * Time: 15:47
     * @param $casid
     * @param int $develop_contact_first_id
     * @param int $develop_contact_second_id
     * @throws AdminException
     * @throws Exception
     * @throws ParameterException
     * @throws SuccessMessage
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function assignContacts($casid, $develop_contact_first_id = 0, $develop_contact_second_id = 0)
    {
        $userBranch = UserBranch::find($casid);
        if(!$userBranch)
        {
            throw new AdminException(['msg'=>'所操作的用户不存在']);
        }
        if($develop_contact_first_id == 0&&$develop_contact_second_id == 0)
        {
            throw new ContactsException(['errorCode'=>50002,'msg'=>'尚未选择培养联系人']);
        }
        if($develop_contact_first_id!=0)
        {
            $userBranch->develop_contact_first_id = $develop_contact_first_id;
        }
        if($develop_contact_second_id!=0)
        {
            $userBranch->develop_contact_second_id = $develop_contact_second_id;
        }
        $res = $userBranch->save();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new Exception('指派培养联系人数据库存储异常');
        }
    }


}