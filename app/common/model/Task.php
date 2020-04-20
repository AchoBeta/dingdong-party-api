<?php


namespace app\common\model;


use app\common\service\Token;
use think\Model;

class Task extends Model
{
    protected $autoWriteTimestamp = true;
    public function onBeforeInsert($model)
    {
        $model->setAttr('general_branch_id',Token::getCurrentTokenVar('general_branch_id'));
    }
    public function user_branch()
    {
        return $this->belongsToMany(UserBranch::class,UserState::class,'task_id','casid');
    }
    public function user()
    {
        return $this->belongsToMany(User::class,UserState::class,'task_id','casid');
    }

}