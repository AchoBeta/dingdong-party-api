<?php


namespace app\common\validate;


class BindCasIdInfoValidate extends BaseValidate
{
    protected $rule = [
        'casId'=>'require|isNotEmpty',
        'name'=>'require|isNotEmpty',
    ];
    protected $message = [
        'casId'=>'学号或工号禁止为空',
        'name' => '姓名不能为空'
    ];
}