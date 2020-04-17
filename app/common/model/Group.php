<?php


namespace app\common\model;


use think\Model;

class Group extends Model
{
    protected $autoWriteTimestamp = true;
    public function user_branch()
    {
        return $this->hasMany(UserBranch::class,'group_id');
    }
    public function user()
    {
        return $this->hasMany(User::class,'group_id');
    }
}