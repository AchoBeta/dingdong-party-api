<?php


namespace app\party\controller\v1;

use app\common\model\UserState as UserStateModel;
use app\common\service\Token;
use app\common\validate\UserStateValidate;
use app\lib\exception\ParameterException;
use app\lib\exception\SuccessMessage;

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
}