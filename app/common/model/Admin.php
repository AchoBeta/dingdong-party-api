<?php


namespace app\common\model;


use think\Model;

class Admin extends Model
{
    public static function checkPwdByCasid($data)
    {
        $admin = self::where('casid',$data['casid'])->find();
        if($admin['password'] == md5($data['password'].config('secure.token_salt')))
        {
            return $admin;
        }else{
            return false;
        }

    }
}