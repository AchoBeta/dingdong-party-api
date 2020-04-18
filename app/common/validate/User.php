<?php


namespace app\common\validate;


class User extends BaseValidate
{
    protected $rule = [
        'id_card'=>'idCard',
        'birth'=>'date',
        'mobile'=>'mobile'
    ];
    protected $message = [
        'id_card'=>'身份证格式不正确',
        'birth'=>'出生年月格式有误',
        'mobile'=>'手机格式不正确'
    ];
    protected $scene = [
        'create'=>['casid','name','gender','nation','origin','id_card','birth','mobile','family_address','institute','grade','major','class','dormitory_no','class_position']
    ];
}