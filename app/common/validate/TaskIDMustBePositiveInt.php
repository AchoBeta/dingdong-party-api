<?php


namespace app\common\validate;


class TaskIDMustBePositiveInt extends BaseValidate
{
    protected $rule = [
        'taskId'=>'require|isPositiveInteger'
    ];
}