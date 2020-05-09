<?php


namespace app\common\model;


use app\common\service\Token;
use think\Model;

class Task extends Model
{
    protected $hidden = [
        'create_time',
        'update_time',
        'delete_time',
        'general_branch_id'
    ];
    protected $autoWriteTimestamp = true;
    public function onBeforeInsert($model)
    {
        $model->setAttr('general_branch_id',Token::getCurrentTokenVar('general_branch_id'));
    }
    public function userBranch()
    {
        return $this->belongsToMany(UserBranch::class,'user_state','casid','task_id');
    }
    public function user()
    {
        return $this->belongsToMany(User::class,'user_state','casid','task_id');
    }
    public static function getNextById($id){
        return self::where(['id'=>$id+1])->find();
    }
}