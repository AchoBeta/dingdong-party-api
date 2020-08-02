<?php


namespace app\party\controller\v1;

use app\common\model\User as UserModel;
use app\common\service\Token;
use app\lib\exception\ResultException;
use app\lib\exception\SuccessMessage;
use app\lib\exception\UserException;

class User
{
    public function index($limit = 10)
    {
        $list = UserModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))->hidden([''])
            ->paginate($limit);
        return json($list);
    }

    public function read($id)
    {
        $list = UserModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->with(['branch','activities','userBranch'=>function($query){
                $query->with(['firstContact','secondContact']);
            }])
            ->hidden(['openId'])
            ->find($id);
        return json($list);
    }

    public function save()
    {
//        $validate = (new \app\common\validate\User());
//        $validate->goCheck('create');
//        $data = $validate->getDataByRule(input(),'create');
        $res = UserModel::create(input());
        if ($res)
        {
            return json($res);
        }else{
            throw new UserException(['msg'=>'用户新增操作异常',70002]);
        }
    }

    public function update($id)
    {
        $user = UserModel::find($id);
        $res = $user->field(['name','gender','nation','origin','id_card','birth','mobile','join_league_time','family_address','institute','grade','major','class','dormitory_no','class_position'])
            ->save(input());
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new UserException(['msg'=>'用户更新失败',70003]);
        }
    }

    public function delete($id)
    {
        $user = UserModel::find($id);
        $res = $user->delete();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new UserException(['msg'=>'用户信息删除异常','errorCode'=>70001]);
        }
    }

    /**
     * Notes:审核用户基本信息
     * User: charl
     * Date: 2020/8/2
     * Time: 9:30
     * @param $id
     * @param $status
     * @param string $reason
     * @throws ResultException
     * @throws SuccessMessage
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function changeAuditStatus($id,$status,$reason = '')
    {
        $user = UserModel::find($id);
        $user->status = $status;
        $user->reason = $reason;
        $res = $user->save();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ResultException(['msg'=>'无法修改该用户审核状态']);
        }
    }



}