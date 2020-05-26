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
    const FILED_ARR = [
        "3" => [
            ["casid","name","gender","nation","origin","id_card","birth","mobile","join_league_time","family_address","institute","grade","major","class","dormitory_area","dormitory_no","class_position","email","resume"],
            ["id","activist_recommended_opinions"],
        ],
        "11" => [
            ["name","mobile","institute","gender","birth"],
            ["id","apply_time","activist_time","develop_contact_first","develop_contact_second","awards_info","disposition_info","investigation_in_half_year_activist"]
        ],
        "25" => [
            ["name","mobile","institute"],
            ["id","opinions_for_supply_activist"]
        ],
        "28" => [
            ["casid","name","gender","nation","origin","birth"],
            ["id","application_info"]
        ],
        "37" => [
            ["casid","name","mobile","gender","birth","institute"],
            ["id","confirm_development_time","probationary_time","branch_opinions_in_half_year_for_prepare"]
        ],
        "42" => [
            ["name","mobile","institute"],
            ["id","opinions_for_supply_prepare"]
        ],
    ];

    public function editApplyNoteInfo(){
        (new EditNoteValidate())->goCheck();
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->find();
        if(!$user){
            throw new UserException();
        }
        $casId = $user->casid;
        $userBranch = UserBranch::where('id',$casId)->find();
        $infoData = input("post.");
        foreach ($infoData as $key => $value){
            $user[$key] = $value;
        }
        $userBranch->activist_recommended_opinions = input("post.activist_recommended_opinions");
        $userBranchRes = $userBranch->save();
        $res = $user->save();
        if($userBranchRes && $res){
            return json(['code'=>200,'msg'=>'保存成功']);
        }else{
            return json(['code'=>300,'msg'=>'保存失败，请稍后重试。']);
        }
    }

    public function editActivistNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = 11;
        $branchArr = ["apply_time","activist_time","awards_info","disposition_info","investigation"];
        foreach ($branchArr as  $value){
            $userBranch[$value] = input("post.$value");
        }
        $userBranch['investigation_in_half_year_activist'] = $userBranch['investigation'];
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
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = 25;
        $activities = input("post.activities");
        $opinions_for_supply_activist = [
            "introduce_opinion_for_activist" => input("post.introduce_opinion_for_activist"),
            "group_opinion_for_activist" => input("post.group_opinion_for_activist"),
            "masses_opinion_for_activist" => input("post.masses_opinion_for_activist"),
            "party_school_train_situation" => input("post.party_school_train_situation"),
        ];
        $userBranch->opinions_for_supply_activist = $opinions_for_supply_activist;
        $userBranchRes = $userBranch->save();
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = ($res&&$userBranchRes)?200:300;
        $msg = ($res&&$userBranchRes)?"保存成功":"保存失败";
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
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = 28;
        $userBranch['application_info'] = $dataArr;
        $userBranchRes = $userBranch->save();
        $activities = input("post.activities");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = ($res&&$userBranchRes)?200:300;
        $msg = ($res&&$userBranchRes)?"保存成功":"保存失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function editPrepareNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        $taskId = 37;
        $taskIdFromSql = (new User())->getUserTask()->id;
        if($taskId!=$taskIdFromSql){
            throw new TaskException(["msg"=>"用户所处的任务阶段不一样。"]);
        }
        $branchArr = ["confirm_development_time","probationary_time","branch_opinions_in_half_year"];
        foreach ($branchArr as  $value){
            $userBranch[$value] = input("post.$value");
        }
        $userBranch['branch_opinions_in_half_year_for_prepare'] = $userBranch['branch_opinions_in_half_year'];
        $branchRes = $userBranch->save();
        $activities = input("post.activities");
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = ($branchRes&&$res)?200:500;
        $msg = ($branchRes&&$res)?"提交成功":"提交失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function supplyPrepareNoteInfo(){
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $taskId = 42;
        $taskIdFromSql = (new User())->getUserTask()->id;
        $userBranch = UserBranch::where(['id'=>$casId])->find();
        if($taskId!=$taskIdFromSql){
            throw new TaskException(["msg"=>"用户所处的任务阶段不一样。"]);
        }
        $dataArr = [];
        foreach ($dataArr as  $value){
            $dataArr[$value] = input("post.$value");
        }
        $userBranch["opinions_for_supply_prepare"] = $dataArr;
        $activities = input("post.activities");
        $userBranchRes = $userBranch->save();
        $res = UserActivity::addUserActivity($activities,$casId,$taskId);
        $code = ($userBranchRes&&$res)?200:500;
        $msg = ($userBranchRes&&$res)?"提交成功":"提交失败";
        return json(['code'=>$code,'msg'=>$msg]);
    }

    public function getNoteInfo($task){
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->with([
            'userBranch'=>function($query) use ($task){
                $query->field(self::FILED_ARR["$task"][1]);
            },
            'activities' => function($query) use ($task){
                $query->where('task_id',$task)->hidden(['user_id',"create_time", "update_time", "delete_time"]);
            }
        ])->visible(self::FILED_ARR["$task"][0])->find();
        if(!$user){
            throw new UserException(["用户信息未录入。"]);
        }
        return json($user);
    }
}