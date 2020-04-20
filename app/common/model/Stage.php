<?php


namespace app\common\model;


use app\common\service\Token;
use think\Model;

class Stage extends Model
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
        return $this->belongsToMany(UserBranch::class,'user_state','casid','stage_id');
    }
    public function user()
    {
        return $this->belongsToMany(User::class,'user_state','casid','stage_id');
    }
    public function userState()
    {
        return $this->hasMany(UserState::class,'stage_id','id');
    }
    public function task()
    {
        return $this->hasMany(Task::class,'stage_id','id');
    }
    public static function getNextOrder($id){
        $order = self::where('id',$id)->find()->value("order")+1;
        return self::where('order',$order)->find();
    }

}