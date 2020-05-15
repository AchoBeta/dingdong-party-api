<?php


namespace app\party\controller\v1;

use app\common\model\UserDetail;
use app\lib\exception\ParameterException;
use app\lib\exception\SuccessMessage;

/**
 * Class Detail
 * @package app\party\controller\v1
 */
class Detail
{
    /**
     * Notes:根据任务id及用户id获取材料审核
     * User: charl
     * Date: 2020/5/15
     * Time: 9:20
     * @param int $task_id
     * @param string $user_id
     * @return \think\response\Json
     * @throws ParameterException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function taskDetail($task_id = 0,$user_id = '')
    {
        $model = UserDetail::where(['task_id'=>$task_id,'user_id'=>$user_id])->find();
        if(!$model)
        {
            throw new ParameterException();
        }
        return json($model);
    }

    /**
     * Notes:根据材料id进行材料审核
     * User: charl
     * Date: 2020/5/15
     * Time: 9:23
     * @param $id
     * @param int $status
     * @param string $reason
     * @throws ParameterException
     * @throws SuccessMessage
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function audit($id,$status = 1,$reason = '')
    {
        $model = UserDetail::find($id);
        if($model->isEmpty())
        {
            throw new ParameterException();
        }
        $model->status = $status;
        $model->reason = $reason;
        $res = $model->save();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ParameterException();
        }
    }
}