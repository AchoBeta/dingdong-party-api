<?php


namespace app\common\model;


use think\Model;

class User extends Model
{
    public function group()
    {
        return $this->belongsTo(Group::class,'group_id');
    }
    public function branch()
    {
        return $this->belongsTo(Branch::class,'branch_id');
    }
    public function activities()
    {
        return $this->hasMany(UserActivity::class,'user_id');
    }
    public function details()
    {
        return $this->hasMany(UserDetail::class,'user_id');
    }
    public function score()
    {
        return $this->hasOne(UserScore::class,'user_id');
    }
}