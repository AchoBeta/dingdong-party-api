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

    public static function addUserActivity($activities,$casId,$taskId,$uid){
        foreach ($activities as &$activity){
            $activity['casid'] = $casId;
            $activity['user_id'] = $uid;
            $activity['task_id'] = $taskId;
        }
        $res = (new UserActivity())->saveAll($activities);
        return $res;
    }

}