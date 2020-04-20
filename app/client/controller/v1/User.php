<?php


namespace app\client\controller\v1;

use app\common\model\User as UserModel;
use app\common\model\Stage as StageModel;
use app\common\model\Task as TaskModel;
use app\common\model\UserState;
use app\common\service\Token as TokenService;
use app\client\service\UserBind;

class User
{
    public function identityForWeChat(){
        $data = input("post.user");
        $encryptedData = $data['encryptedData'];
        $iv = $data['iv'];
        $userBind = new UserBind();
        $userBind->bindUserInfo($encryptedData,$iv);
    }

    public function addUser(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $userData = input("post.");
        $userData['uid'] = $uid;
        $userData['openId'] = $openId;
        $userData['join_league_time'] = time();
        $res = UserModel::add($userData);
        return json($res);
    }

    public function getStage(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $stage_id = UserState::where('casid',$casId)->find()->value("stage_id");
        $stage = StageModel::where("id",$stage_id)->with("task")->find();
        return json($stage);
    }

    public function getTask(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $task_id = UserState::where('casid',$casId)->find()->value("task_id");
        $stage = TaskModel::where("id",$task_id)->find();
        return json($stage);
    }

}