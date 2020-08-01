<?php


namespace app\common\model;


use think\Model;

class UserDetail extends Model
{
    protected $autoWriteTimestamp = true;

    protected $json = ['remarks'];

    public function getUrlAttr($value){
        return config('setting.storage_base_url').$value;
    }
    public function getAuditUrlAttr($value){
        return config('setting.storage_base_url').$value;
    }

    public function user()
    {
        return $this->belongsTo(User::class,'user_id');
    }
}