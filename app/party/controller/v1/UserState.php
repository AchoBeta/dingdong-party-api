<?php


namespace app\party\controller\v1;

use app\common\model\UserState as UserStateModel;
use app\common\service\Token;
use app\common\validate\UserStateValidate;
use app\lib\exception\ParameterException;
use app\lib\exception\SuccessMessage;
use think\Exception;

/**
 * Class UserState
 * Des:用户阶段任务变动控制器
 * @package app\party\controller\v1
 */
class UserState
{
    //已有替代方案
//    public function index()
//    {
//        $validate = new UserStateValidate();
//        $validate->goCheck('index');
//        $params = $validate->getDataByRule(input(),'index');
//        $userState = UserStateModel::where($params)
//            ->with(['user_branch'])
//            ->find();
//    }
    /**
     * Notes:更新用户当前所处状态
     * User: charl
     * Date: 2020/5/18
     * Time: 11:14
     * @throws Exception
     * @throws ParameterException
     * @throws SuccessMessage
     * @throws \app\lib\exception\TokenException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function update()
    {
        $validate = new UserStateValidate();
        $validate->goCheck('update');
        $params = $validate->getDataByRule(input(),'update');
        $model = UserStateModel::where(['casid'=>$params['casid'],'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])
            ->find();
        if(!$model)
        {
            throw new ParameterException();
        }
//        $stage_id = $model['stage_id'];
//        $task_id = $model['task_id'];
        $res = $model->save($params);
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ParameterException();
        }
    }

    /**
     * Notes:重置用户状态
     * User: charl
     * Date: 2020/5/18
     * Time: 11:28
     * @param $casid
     * @throws Exception
     * @throws ParameterException
     * @throws SuccessMessage
     * @throws \app\lib\exception\TokenException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function resetState($casid)
    {
        $model = UserStateModel::where(['casid'=>$casid,'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])
            ->find();
        if(!$model)
        {
            throw new ParameterException();
        }
        $stage_model = \app\common\model\Stage::where(['order'=>1,'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])->find();
        $stage_id = $stage_model->id;
        $task_model = \app\common\model\Task::where(['stage_id'=>$stage_id,'order'=>1])->find();
        $task_id = $task_model->id;
        $model->stage_id = $stage_id;
        $model->task_id = $task_id;
        $res = $model->save();
        if ($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ParameterException();
        }

    }

    /**
     * Notes:下一个任务
     * User: charl
     * Date: 2020/5/18
     * Time: 11:12
     * @throws Exception
     * @throws ParameterException
     * @throws SuccessMessage
     * @throws \app\lib\exception\TokenException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function next_mission()
    {
        $casid = input('casid');
        $model = UserStateModel::where(['casid'=>$casid,'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])
            ->find();
        if(!$model)
        {
            throw new ParameterException();
        }
        $task_id = $model['task_id'];
        $taskModel = \app\common\model\Task::find($task_id);
        if(!$taskModel)
        {
            throw new Exception('内部错误');
        }
        $stage_id = $model['stage_id'];
        $target_order = $taskModel['order'] + 1;
        $re = \app\common\model\Task::where(['stage_id'=>$stage_id,'order'=>$target_order])->find();
        if(!$re)
        {
            if(!$this->next_stage())
            {
                throw new Exception('内部错误');
            }else{
                throw new SuccessMessage();
            }
        }
        $res = $model->save([
            'stage_id'=>$re['stage_id'],
            'task_id'=>$re['id'],
            'status'=>0,
            'reason'=>null
        ]);
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ParameterException();
        }
    }

    /**
     * Notes:下一个阶段
     * User: charl
     * Date: 2020/5/18
     * Time: 11:12
     * @return bool
     * @throws Exception
     * @throws ParameterException
     * @throws \app\lib\exception\TokenException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    protected function next_stage()
    {
        $casid = input('casid');
        $model = UserStateModel::where(['casid'=>$casid,'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])
            ->with(['stage'])
            ->find();
        if(!$model)
        {
            throw new ParameterException();
        }
        $stage_id = $model['stage']['id'];
        $target_order = $model['stage']['order'] + 1;
        $targetStageModel = \app\common\model\Stage::where(['order'=>$target_order,'general_branch_id'=>Token::getCurrentTokenVar('general_branch_id')])
            ->find();
        // The last will send a error
        if(!($targetStageModel->id??null))
        {
            return true;
        }
        // The last
        $target_stage_id = $targetStageModel->id;
        $targetTaskModel = \app\common\model\Task::where(['order'=>1,'stage_id'=>$target_stage_id])
            ->find();
        $target_task_id = $targetTaskModel->id;
        if(!$targetTaskModel)
        {
            throw new ParameterException();
        }
        $res = $model->save([
            'stage_id' => $target_stage_id,
            'task_id' => $target_task_id,
            'status'=>0,
            'reason'=>null
        ]);
        if($res)
        {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Notes:更改当前阶段任务审核状态
     * User: charl
     * Date: 2020/8/3
     * Time: 15:32
     * @param $casid
     * @param $status
     * @throws Exception
     * @throws SuccessMessage
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function audit($casid, $status)
    {
        $userState = UserStateModel::where(['casid'=>$casid])->find();
        if(input('?reason')&&input('reason'))
        {
            $userState->reason = input('reason');
        }
        $userState->status = $status;
        $res = $userState->save();
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new Exception('数据保存失败');
        }
    }
}