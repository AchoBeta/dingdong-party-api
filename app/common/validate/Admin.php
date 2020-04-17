<?php


namespace app\common\validate;


class Admin extends BaseValidate
{
    protected $rule = [
//        'code'=>'require|isNotEmpty',
        'casid'=>'require|isNotEmpty',
        'password'=>'require|isNotEmpty',
        'mobile'=>'mobile'
    ];
    protected $message = [
//        'code'=>'缺少参数CODE',
        'casid'=>'学号或工号禁止为空',
        'password'=>'密码为空',
        'mobile'=>'手机格式有误'
    ];
    protected $scene = [
//        'user'=>['code'],
        'register'=>['casid','password','email','mobile','position']
    ];
}