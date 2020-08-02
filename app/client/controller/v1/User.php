<?php


namespace app\client\controller\v1;

use app\common\model\User as UserModel;
use app\common\model\Stage as StageModel;
use app\common\model\Task as TaskModel;
use app\common\model\UserActivity;
use app\common\model\UserBranch;
use app\common\model\UserState;
use app\common\service\Token;
use app\common\service\Token as TokenService;
use app\client\service\UserBind;
use app\common\validate\BindCasIdInfoValidate;
use app\common\validate\EditNoteValidate;
use app\common\validate\IDMustBePostiveInt;
use app\common\validate\TaskIDMustBePositiveInt;
use app\lib\enum\TaskNeedMaterialEnum;
use app\lib\exception\TaskException;
use app\lib\exception\UserException;

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
        $task = TaskModel::where("id",$task_id)->find();
        return json($task);
    }

    public function bindCasId(){
        (new BindCasIdInfoValidate())->goCheck();
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = input("post.casId");
        $name = input("post.name");
        $hasBind = UserModel::where('openId',$openId)->find();
        if($hasBind)
            return json(['code'=>401,'msg'=>"你的账号已经绑定过了"]);
        $stu = UserModel::where(['casid'=>$casId,'name'=>$name])->find();
        if(!$stu)
            return json(['code'=>401,'msg'=>"系统找不到你的学号信息，请联系相关人员"]);
        if($stu->openId!=null)
            return json(['code'=>301,'msg'=>"该学号已经被绑定了"]);
        $stu->openId = $openId;
        $res = $stu->save();
        $msg = $res?"绑定成功":"绑定失败";
        $code = $res?200:500;
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function getUserTask(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $task_id = UserState::where('casid',$casId)->value("task_id");
        $task = TaskModel::where("id",$task_id)->find();
        return $task;
    }

    public function getMaterialUrl($taskId){

    }

    public function checkUserBind(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar("openid");
        $casId = UserModel::where("openId",$openId)->value("casid");
        $hasBind = $casId?true:false;
        $code = $casId?"200":"401";
        return json(['casId'=>$casId,"result"=>$hasBind,'code'=>$code]);
    }

    public function getUserDevelopContacts(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar("openid");
        $casId = UserModel::where("openId",$openId)->value("casid");
        $userBranch = UserBranch::where('id',$casId)->with(['firstContact','secondContact'])->find();
        return json(['firstDevelopContact'=>$userBranch['firstContact'],'secondDevelopContact'=>$userBranch['secondContact']]);
    }

    public function getUserRecommendContacts(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar("openid");
        $casId = UserModel::where("openId",$openId)->value("casid");
        $userBranch = UserBranch::where('id',$casId)->with(['firstRecommendContact','secondRecommendContact'])->find();
        return json(['firstRecommendContact'=>$userBranch['firstRecommendContact'],'secondRecommendContact'=>$userBranch['secondRecommendContact']]);
    }


    public function getUserInfo(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->field(['casid,name,class'])->find();
        $user['branch_name'] = "网络空间安全学院党支部";
        return json($user);
    }

    public function cancelBind(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $res = UserModel::where('openId',$openId)->update(['openId'=>null]);
        $code = $res?200:500;
        $msg = $res?"解绑成功":"解绑失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }
}