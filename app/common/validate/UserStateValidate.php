<?php


namespace app\common\validate;


class UserStateValidate extends BaseValidate
{
    protected $rule = [
        'stage_id'=>'isPositiveInteger',
        'task_id'=>'isPositiveInteger'
    ];
    protected $message = [
        'stage_id'=>'阶段参数异常',
        'task_id'=>'任务参数异常'
    ];
    protected $scene = [
        'update'=>['stage_id','task_id','casid']
    ];
}