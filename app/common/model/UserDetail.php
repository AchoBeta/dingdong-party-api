<?php


namespace app\common\model;


use think\Model;

class UserDetail extends Model
{
    protected $autoWriteTimestamp = true;

    protected $json = ['remarks'];

    public function getUrlAttr($value){
        return "https://hua-bang.club/Party/public/storage/".$value;
    }

    public function user()
    {
        return $this->belongsTo(User::class,'user_id');
    }
}