<?php


namespace app\common\validate;


class IDMustBePostiveInt extends BaseValidate
{
    protected $rule = [
        'id'=>'require|isPositiveInteger'
    ];

}