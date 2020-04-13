<?php


namespace app\party\controller\v1;
use app\common\validate\Admin as AdminValidate;
use app\common\model\Admin as AdminModel;
use app\lib\exception\AdminException;
use app\lib\exception\SuccessMessage;
use app\lib\exception\UserException;
use think\Exception;

class Admin
{
    public function adminRegister()
    {
        $validate = new AdminValidate();
        $validate->goCheck('register');
        $dataArray = $validate->getDataByRule(input(),'register');
//        $user_model = new UserModel();
        $is_exist = AdminModel::where('casid','=',$dataArray['casid'])->find();
        $dataArray['password'] = md5($dataArray['password'].config('secure.token_salt'));
        if($is_exist)
        {
            $res = $is_exist->save($dataArray);
        }else{
            throw new AdminException();
        }
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new AdminException(['msg'=>'管理员注册失败','errorCode'=>80001]);
        }
    }
    public function test()
    {
        dump(config('log.default'));
    }
}