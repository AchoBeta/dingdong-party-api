<?php


namespace app\common\model;


use think\Model;

class Branch extends Model
{
    public function general_branch()
    {
        return $this->belongsTo(GeneralBranch::class,'general_branch_id','id');
    }
    public function user()
    {
        return $this->hasMany(User::class,'branch_id');
    }
    public function user_branch()
    {
        return $this->hasMany(UserBranch::class,'branch_id');
    }

}