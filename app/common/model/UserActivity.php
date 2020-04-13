<?php


namespace app\common\model;


use think\Model;

class UserActivity extends Model
{
    public function user()
    {
        return $this->belongsTo(User::class,'user_id');
    }

}