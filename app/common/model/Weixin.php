<?php


namespace app\common\model;


use think\Model;

class Weixin extends Model
{
    protected $autoWriteTimestamp = true;

    public static function getByOpenId($openid){
        return self::where('openId',$openid)->find();
    }

}