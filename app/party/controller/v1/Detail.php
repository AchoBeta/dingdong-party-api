<?php


namespace app\party\controller\v1;

use app\common\model\UserDetail;
use app\common\service\UploadTool;
use app\lib\exception\ParameterException;
use app\lib\exception\SuccessMessage;
use app\lib\exception\UploadException;

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
            throw new ParameterException(['msg'=>'用户尚未提交审核材料']);
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
            throw new ParameterException(['msg'=>'审核异常']);
        }
    }

    /**
     * Notes:根据材料id进行材料修改版本上传
     * User: charl
     * Date: 2020/5/18
     * Time: 11:11
     * @param $id
     * @return \think\response\Json
     * @throws ParameterException
     * @throws UploadException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function uploadDetailDoc($id)
    {
        $files = request()->file();
        if(!($files??null))
        {
            throw new ParameterException();
        }
        $tool = new UploadTool();
        $upload_res = $tool->file_upload($files);
        $model = UserDetail::find($id);
        if(!$model||$model->isEmpty())
        {
            throw new ParameterException();
        }
        $model->audit_url = $upload_res[0]['url'];
        $res = $model->save();
        if($res)
        {
            return json($upload_res);
        }else{
            throw new UploadException();
        }
    }
}