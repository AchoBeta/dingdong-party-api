<?php


namespace app\common\model;


use think\Model;

class UserActivity extends Model
{
    protected $autoWriteTimestamp = true;
    public function user()
    {
        return $this->belongsTo(User::class,'user_id');
    }

}