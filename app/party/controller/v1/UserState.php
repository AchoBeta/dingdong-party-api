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
            'task_id'=>$re['id']
        ]);
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new ParameterException();
        }
    }
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
            'task_id' => $target_task_id
        ]);
        if($res)
        {
            return true;
        }else{
            return false;
        }
    }
}