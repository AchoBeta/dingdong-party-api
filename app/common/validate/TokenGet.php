<?php


namespace app\common\validate;


class TokenGet extends BaseValidate
{
    protected $rule = [
        'code'=>'require|isNotEmpty',
        'casid'=>'require|isNotEmpty',
        'password'=>'require|isNotEmpty'
    ];
    protected $message = [
        'code'=>'缺少参数CODE',
        'casid'=>'学号或工号禁止为空',
        'password'=>'密码为空'
    ];
    protected $scene = [
        'user'=>['code'],
        'admin'=>['casid','password']
    ];

}