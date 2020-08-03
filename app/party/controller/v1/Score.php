<?php


namespace app\party\controller\v1;


use app\common\model\UserScore;
use app\lib\exception\SuccessMessage;
use think\Exception;

class Score
{
    /**
     * Notes:手动录入学生分党校考试及校党课考试
     * User: charl
     * Date: 2020/8/3
     * Time: 17:01
     * @param $type
     * @param $casid
     * @param int $branch_school_score
     * @param int $school_score
     * @throws Exception
     * @throws SuccessMessage
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function exam_score($casid, $branch_school_score = -1, $school_score = -1)
    {
        $userScore = UserScore::where(['casid'=>$casid])->find();
        if(!$userScore)
        {
            $userScore = UserScore::create(['casid'=>$casid]);
        }
        if($branch_school_score != -1)
        {
            $userScore->branch_school_score = $branch_school_score;
        }
        if($school_score != -1)
        {
            $userScore->school_score = $school_score;
        }
        $res = $userScore->save();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new Exception('数据保存失败');
        }
    }
}