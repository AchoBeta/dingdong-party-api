<?php


namespace app\client\controller\v1;


use app\common\model\GeneralBranch;
use app\common\model\User as UserModel;
use app\common\model\UserActivity;
use app\common\model\UserBranch;
use app\common\model\UserDetail;
use app\common\service\Token as TokenService;
use app\common\validate\EditNoteValidate;
use app\lib\exception\TaskException;
use app\lib\exception\UserException;
use PhpOffice\PhpWord\TemplateProcessor;

class Note
{
    const LOCAL_STATIC_URL = "C:/xampp/virtualhost/Party/public/";
    const URL_ATTR = "https://www.dingdongtongxue.com/party/public/storage/";
    const FILE_FILED_ARR = [
        'apply' => ['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
        'activist' =>['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
        "supplyActivist"=>['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
        "application"=>['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
        "Prepare"=>['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
        "supplyPrepare"=>['name','mobile','institute','gender','nation','origin','birth','casId','join_league_time','id_card','grade','dormitory_area','dormitory_no','class_position','class','email','family_address','resume'],
    ];

    const SAVE_PATH = "https://www.dingdongtongxue.com/party/public/";
    const FILED_ARR_NAME = [
        'apply' => '入党申请人培养考察手册',
        'activist' =>'入党积极分子培养考察手册',
        'supplyActivist' =>'入党积极分子培养考察手册(补充后)',
        'application' => '中国共产党入党志愿书',
        'Prepare' => '预备党员培养考察手册',
        'supplyPrepare' => '预备党员培养考察手册(补充后)',
    ];
    const FILED_ARR = [
        "1" => [
            ["id","casid","name","gender","nation","origin","id_card","birth","mobile","join_league_time","family_address","institute","major","grade","class","dormitory_area","dormitory_no","email","resume"],
            ["id","branch_id","current_identity"]
        ],
        "3" => [
            ["casid","name","gender","nation","origin","id_card","birth","mobile","join_league_time","family_address","institute","grade","major","class","dormitory_area","dormitory_no","class_position","email","resume"],
            ["id","branch_id","activist_recommended_opinions"],
        ],
        "11" => [
            ["name","mobile","institute","gender","birth"],
            ["id","branch_id","apply_time","activist_time","develop_contact_first_id","develop_contact_second_id","awards_info","disposition_info","investigation_in_half_year_activist"]
        ],
        "25" => [
            ["name","mobile","institute"],
            ["id","branch_id","opinions_for_supply_activist"]
        ],
        "28" => [
            ["casid","name","gender","nation","origin","birth"],
            ["id","application_info","apply_time","branch_id","confirm_development_time",'probationary_time']
        ],
        "37" => [
            ["casid","name","mobile","gender","birth","institute"],
            ["id","branch_id","confirm_development_time","probationary_time","branch_opinions_in_half_year_for_prepare"]
        ],
        "42" => [
            ["name","mobile","institute"],
            ["id","branch_id","opinions_for_supply_prepare"]
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
        $res = UserActivity::addUserActivity($activities,$casId,$taskId,$uid);
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
        $res = UserActivity::addUserActivity($activities,$casId,$taskId,$uid);
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
        if($activities!=null)
            $res = UserActivity::addUserActivity($activities,$casId,$taskId,$uid);
        else{
            $res = true;
        }
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
        $res = UserActivity::addUserActivity($activities,$casId,$taskId,$uid);
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
        $res = UserActivity::addUserActivity($activities,$casId,$taskId,$uid);
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
            $branch_id = $user['userBranch']['branch_id'];
            $user['userBranch']['branch_name'] = GeneralBranch::where('id',$branch_id)->value('name');
            $user['userBranch']['second_branch'] = $user['userBranch']['branch_name'];
        if(!$user){
            throw new UserException(["用户信息未录入。"]);
        }
        return json($user);
    }

    public function getAllNoteInfo(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->with([
            'userBranch',
            'activities'
        ])->find();
        $branch_id = $user['userBranch']['branch_id'];
        $user['userBranch']['branch_name'] = GeneralBranch::where('id',$branch_id)->value('name');
        $user['userBranch']['second_branch'] = $user['userBranch']['branch_name'];
        if(!$user){
            throw new UserException(["用户信息未录入。"]);
        }
        return json($user);
    }

    public function getUserFirstScore(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $score = UserBranch::where('id',$casId)->value('first_score');
        $status = $score>70?'通过':'不通过';
        return json(['score'=>$score,'status'=>$status]);
    }

    public function getUserSecondScore(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $score = UserBranch::where('id',$casId)->value('second_score');
        $status = $score>70?'通过':'不通过';
        return json(['score'=>$score,'status'=>$status]);
    }

    public function generateNote($fileStage,$flag = false){
        $openId = TokenService::getCurrentTokenVar('openid');
        $user = UserModel::where('openId',$openId)->with([
            'branch',
            'userBranch',
            'activities'
        ])->find();
        $templateProcessor = new TemplateProcessor(self::LOCAL_STATIC_URL."temple/templeFinal.docx");
        $filedArr = self::FILE_FILED_ARR[$fileStage];
        $arr = [];
        foreach ($filedArr as $key => $value){
            $arr[$value] = $user[$value];
        }
        $arr['general_branch_name'] = $user['branch']['name'];
        $arr['casId'] = $user['id'];
        $casId = $user['id'];
        $arr['gender'] = $user['gender'] ==1?"男":"女";
        foreach ($arr as $key => $value)
            $templateProcessor->setValue($key, $value);
        $fileName = self::LOCAL_STATIC_URL."storage/stuWord/$casId/".self::FILED_ARR_NAME[$fileStage].".docx";
        $templateProcessor->saveAs($fileName);
        $url = self::SAVE_PATH."storage/stuWord/$casId/".self::FILED_ARR_NAME[$fileStage].".docx";
        if(!$flag)
            return json(['code'=>200,'url'=>$url]);
        else
            return $url;
    }

    public function getAllFilesLink(){
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $applyUrl = UserDetail::where(['user_id'=>$casId,'task_id'=>1])->value('url');
        $becomeFullUrl = UserDetail::where(['user_id'=>$casId,'task_id'=>40])->value('url');
        $think_reportUrl = UserDetail::where(['user_id'=>$casId,'task_id'=>21])->value('url');
        $memoirUrl = UserDetail::where(['user_id'=>$casId,'task_id'=>22])->value('url');
        $arr = [
            'apply' => [
                'name' => '入党申请书',
                'link' => $applyUrl?self::URL_ATTR.$applyUrl:null
            ],
            'prepare' => [
                'name' => '预备党员培养考察手册',
                'link' => $this->generateNote('Prepare',true)
            ],
            'application' => [
                'name' => '中国共产党入党志愿书（部分）',
                'link' => $this->generateNote('Prepare',true)
            ],
            'becomeFull' => [
                'name' => '转正申请书',
                'link' => $becomeFullUrl?self::URL_ATTR.$becomeFullUrl:null
            ],
            'think_report' => [
                'name' => '思想汇报',
                'link' =>  $think_reportUrl?self::URL_ATTR.$think_reportUrl:null
            ],
            'memoir' => [
                'name' => '自传',
                'link' => $memoirUrl?self::URL_ATTR.$memoirUrl:null
            ]
        ];
        return json($arr);
    }
}