<?php


namespace app\common\model;


use app\lib\exception\UserAddException;
use think\Exception;
use think\Model;
use think\model\concern\SoftDelete;

class User extends Model
{
    protected $autoWriteTimestamp = true;
    use SoftDelete;
    public function group()
    {
        return $this->belongsTo(Group::class,'group_id');
    }
    public function branch()
    {
        return $this->belongsTo(Branch::class,'branch_id');
    }
    public function generalBranch()
    {
        return $this->belongsTo(GeneralBranch::class,'general_branch_id');
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
    public static function add($userData){
        $openId = $userData['openId'];
        $casId = $userData['casid'];
        $openIdHasBind = self::where("openId",$openId)->find();
        if($openIdHasBind){
            throw new UserAddException(['msg'=>"openId已经被绑定"]);
        }
        $casIdHasBind = self::where("casid",$casId)->find();
        if($casIdHasBind){
            throw new UserAddException(['msg'=>"学号已经被绑定"]);
        }
        $res = (new User())->save($userData);
        return $res;
    }
}