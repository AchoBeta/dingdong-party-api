<?php


namespace app\client\controller\v1;


use app\common\model\User as UserModel;
use app\common\model\UserActivity;
use app\common\model\UserBranch;
use app\common\service\Token as TokenService;
use app\common\validate\EditNoteValidate;
use app\lib\exception\TaskException;
use app\lib\exception\UserException;

class Note
{
    public function editApplyNoteInfo(){
        (new EditNoteValidate())->goCheck();
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->find();
        if(!$user){
            throw new UserException();
        }
        $infoData = input("post.");
        foreach ($infoData as $key => $value){
            $user[$key] = $value;
        }
        $user->save();
        return json($user);
    }

    public function editActivistNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = input("post.task_id");
        $branchArr = ["apply_time","activist_time","awards_info","disposition_info"];
        foreach ($branchArr as  $value){
            $userBranch[$value] = input("post.$value");
        }
        $userBranch->save();
        $activities = input("post.activities");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = $res ? 200 : 500;
        $msg = $res ? "修改成功" : "修改失败";
        return json(['code'=>$code,'mgs'=>$msg]);
    }

    public function supplyActivistNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $taskId = input("post.taskId");
        $activities = input("post.activities");
        $introduce_opinion_for_activist = input("post.introduce_opinion_for_activist");
        $group_opinion_for_activist = input("post.group_opinion_for_activist");
        $masses_opinion_for_activist = input("post.masses_opinion_for_activist");
        $party_school_train_situation = input("post.party_school_train_situation");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        //TODO:字段存放入对应的数据库
        $code = $res?200:300;
        $msg = $res?"保存成功":"保存失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function editApplicationNoteInfo(){
        $paramArr = ["birthplace","education","duty","current_habitation","expertise","aspiration","experience","family_members","problem_to_party","introduce_opinion_for_application"];
        $dataArr = [];
        foreach ($paramArr as $param){
            $dataArr[$param] = input("post.$param");
        }
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        //TODO: to get it after database
    }

    public function editPrepareNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = input("post.task_id");
        $taskIdFromSql = (new User())->getUserTask()->id;
        if($taskId!=$taskIdFromSql){
            throw new TaskException(["msg"=>"用户所处的任务阶段不一样。"]);
        }
        $branchArr = ["confirm_development_time","probationary_time","branch_opinions_in_half_year"];
        foreach ($branchArr as  $value){
            $userBranch[$value] = input("post.$value");
        }
        $userBranch->save();
        $activities = input("post.activities");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = $res?200:500;
        $msg = $res?"提交成功":"提交失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function supplyPrepareNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $taskId = input("post.task_id");
        $taskIdFromSql = (new User())->getUserTask()->id;
        if($taskId!=$taskIdFromSql){
            throw new TaskException(["msg"=>"用户所处的任务阶段不一样。"]);
        }
        $dataArr = ["introduce_opinion_for_prepare","group_opinion_for_prepare","masses_opinion_for_prepare"];
        foreach ($dataArr as  $value){
            $dataArr[$value] = input("post.$value");
        }
        //TODO: after database
        $activities = input("post.activities");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = $res?200:500;
        $msg = $res?"提交成功":"提交失败";
        return json(['code'=>$code,'msg'=>$msg,'dataArr'=>$dataArr]);
    }
}