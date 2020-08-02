<?php


namespace app\client\controller\v1;
use app\common\model\Upload;
use app\common\model\UserDetail;
use app\common\model\UserState;
use app\common\model\Weixin;
use app\common\model\User as UserModel;
use app\common\validate\TaskIDMustBePositiveInt;
use app\lib\enum\MaterialStatusEnum;
use app\lib\enum\TaskNeedMaterialEnum;
use app\lib\exception\FileException;
use app\lib\exception\TaskException;
use GuzzleHttp\Client;
use PhpOffice\PhpWord\PhpWord;
use PhpOffice\PhpWord\IOFactory;
use PhpOffice\PhpWord\TemplateProcessor;
use app\common\service\Token as TokenService;
use think\exception\ValidateException;

class File
{
    const PASS_SCORE = 70;
    /**
     * Notes:用户上传材料进行审核
     * @param $taskId 对应的任务id
     * User: hua-bang
     * Date: 2020/5/10
     * Time: 16:58
     * @return \think\response\Json
     * @throws TaskException
     * @throws \app\lib\exception\ParameterException
     * @throws \app\lib\exception\TokenException
     * @throws \think\Exception
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */

    public function uploadMaterial($taskId){
        (new TaskIDMustBePositiveInt())->goCheck();
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        if(!in_array($taskId,TaskNeedMaterialEnum::TASK_NEED_MATERIAL_ARR)){
            throw new TaskException(['msg'=>'该任务不需要提交材料']);
        }
        $casId = UserModel::where('openId',$openId)->value("casid");
        $taskIdFromSql = UserState::where('casid',$casId)->value("task_id");
//        if($taskIdFromSql!=$taskId){
//            throw new TaskException(['msg'=>'任务不属于用户当前阶段']);
//        }
        $needCheck = in_array($taskId,TaskNeedMaterialEnum::TASK_NEED_CHECK);
        $res = $this->upload($taskId,$casId,$needCheck);
        return json($res);
    }

    protected function upload($taskId,$casId,$needCheck=false){
        $uid = TokenService::getCurrentUid();
        $userUploadMsg = UserDetail::where(['user_id'=>$casId,"task_id"=>$taskId])->find();
        $hasUpload = $userUploadMsg?true:false;
        if($hasUpload){
            $casId = $userUploadMsg->casid;
            if($needCheck){
                if($userUploadMsg->status >= MaterialStatusEnum::NO_CHECKED){
                    $msg = $userUploadMsg->status==MaterialStatusEnum::NO_CHECKED?"你的材料正处于管理员审核阶段，请耐心等候":'你的材料已经通过审核了，请勿重复提交.';
                    return ['code'=>200,'status'=>$userUploadMsg->status,'msg'=>$msg];
                }
            }
            else{
                if($userUploadMsg->status >= MaterialStatusEnum::NO_CHECKED){
                    $msg = $userUploadMsg->status==MaterialStatusEnum::NO_CHECKED?"你的材料正处于管理员审核阶段，请耐心等候":'你的材料已经通过审核了，请勿重复提交.';
                    return ['code'=>200,'status'=>$userUploadMsg->status,'msg'=>$msg];
                }
            }
        }else{
            $openId = Weixin::where('id',$uid)->value('openId');
            $casId = UserModel::where('openId',$openId)->value('casId');
            $userUploadMsg = [
                'user_id' => $casId,
                'casid' => $casId,
                'task_id' => $taskId
            ];
        }
        $file = request()->file();
        if(count($file)==0){
            throw new FileException();
        }
        try{
            validate(['file'=>'filesize:3145728|fileExt:docx'])
                ->check($file);
        }catch (ValidateException $e){
            throw new FileException(['msg'=>"文件验证不通过。"]);
        }
        $rule = function () use ($taskId){
            return TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];
        };
        $saveName = \think\facade\Filesystem::disk('public')->putFile( "stuWord/$casId", $file['file'],$rule);
        $saveFile = [
            'savepath' => $saveName,
            'url' => $saveName,
            'file_mime' => $file['file']->getOriginalMime(),
            'file_name' => $file['file']->getOriginalName(),
            'ext' => $file['file']->getOriginalExtension(),
            'size' => round($file['file']->getSize()/1024/1024,2),
            'ip'=>request()->ip()
        ];
        $upload = new Upload();
        $res = $upload->save($saveFile);
        $scorePass = false;
        if($needCheck){
            $path = "C:/inetpub/wwwroot/Party/public/storage/".$saveName;
            $content = $this->getFileStr($path);
            $result = $this->checkOriginal($content);
            $scorePass = $result['result']['score']>self::PASS_SCORE?true:false;
            $sentenceArr = [];
            foreach ($result['result']['sentence'] as $sentence){
                if($sentence['resemble_value']>0)
                    array_push($sentenceArr,$sentence);
            }
            if($hasUpload){
                $userUploadMsg->remarks = [
                    "score" => $result['result']['score'],
                    'res' => $sentenceArr
                ];
            }else{
                $userUploadMsg['remarks'] = [
                    "score" => $result['result']['score'],
                    'res' => $sentenceArr,
                ];
            }
        }else{
            $res = true;
        }
        $status = $needCheck?($scorePass?MaterialStatusEnum::NO_CHECKED:MaterialStatusEnum::NO_PASS_BY_AI):MaterialStatusEnum::NO_CHECKED;
        $msg = $needCheck?($scorePass==true?"通过自动查重审核，等待管理员审核":"自动查重审核不通过，请检查文章的原创性"):"文件上传成功，等待管理员审核";
        if($hasUpload){
            $userUploadMsg->material_name = TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];
            $userUploadMsg->status = $status;
            $userUploadMsg->url = $saveName;
            $userUploadMsg->save();
        }else{
            $userUploadMsg['material_name'] = TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];;
            $userUploadMsg['status'] = $status;
            $userUploadMsg['url'] = $saveName;
            unset($userUploadMsg['casid']);
            $insertSuccess = UserDetail::insert($userUploadMsg);
            $msg = $insertSuccess?$msg:"上传失败，请重试";
        }
        $code = $res ? 200:300;
        $finalRes = $needCheck?(['msg'=>$msg,'code'=>$code,'status'=>$status,'score'=>$result['result']['score'],'sentence'=>$sentenceArr]):(['msg'=>$msg,'status'=>$status,'code'=>$code]);
        return $finalRes;
    }

    protected function getFileStr($path){
        $arr = explode('.',$path);
        $ext = array_pop($arr);
        $sources = IOFactory::load($path)->getSections();
        $str = '';
        foreach ($sources as $section) {
            foreach ($section->getElements() as $ele1) {
                if ($ele1 instanceof \PhpOffice\PhpWord\Element\TextRun) {
                    foreach ($ele1->getElements() as $ele2) {
                        if ($ele2 instanceof \PhpOffice\PhpWord\Element\Text) {
                            $str .= $ele2->getText();
                        }
                    }
                }
            }
        }
        return $str;
    }

    public function generateNote(){
        $casId = 201841413207;
        $templateProcessor = new TemplateProcessor("temple/noteTemple2.docx");
        $arr = [
            "name" => "胡佳华",
            "mobile" => 13413943339,
            "institute" => "网络空间安全学院",
            "sex" => "男",
            "nation" => "汉",
            "origin" => "广东揭阳",
            "birthday" => "2000年11月24日",
            "casId" => $casId,
            "join_league_time" => "2015年10月24日",
            "id_card" => '445249200110100011',
            'grade' => '2018',
            "dormitory_area" => "莞博",
            "dormitory_no" => "13栋308",
            "class_position" => "班长",
            'general_branch_name'=>'网络空间安全学院党支部',
            'class' => '软件工程1班',
            'email' => "1964760211@qq.com",
            "family_address" => "广东揭阳",
        ];
        foreach ($arr as $key => $value)
            $templateProcessor->setValue($key, $value);
        $fileName = time()."$casId.docx";
        $templateProcessor->saveAs($fileName);
        $templateProcessor->save();
    }

    protected function checkOriginal($content){
        $tempArr = ['入党申请书敬爱的党组织：'];
        $content = str_replace($tempArr,'',$content);
        $apiUrl = config("api.checkOriginalApi");
        $data = [
            "content" => base64_encode($content),
            "appcode" => config("api.originalApiAppCode")
        ];
        $client = new Client();
        $request = $client->request("post",$apiUrl,[
            "json" => $data
        ]);
        $response = json_decode($request->getBody()->getContents(),true);
        return $response;
    }

    public function MaterialStatus($taskId){
        (new TaskIDMustBePositiveInt())->goCheck();
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $casId = UserModel::where('openId',$openId)->value("casid");
        $userUploadMsg = UserDetail::where(['user_id'=>$casId,'task_id'=>$taskId])->find();
        if(!$userUploadMsg){
            throw new TaskException(['msg'=>"你还没有上传这方面的材料呢。"]);
        }
        if($userUploadMsg->status == MaterialStatusEnum::NO_PASS_BY_ADMIN){
            return json(['code'=>300,'status'=>$userUploadMsg->status,'msg'=>"管理员不通过你的审核,详情请看文件备注和不通过原因。",'reason'=>$userUploadMsg->reason,'audit_url'=>$userUploadMsg->audit_url,'remarks'=>$userUploadMsg->remarks]);
        }
        else if($userUploadMsg->status >= MaterialStatusEnum::NO_CHECKED&&$userUploadMsg->status != MaterialStatusEnum::NO_PASS_BY_ADMIN){
            $msg = $userUploadMsg->status==MaterialStatusEnum::NO_CHECKED?"你的材料正处于管理员审核阶段，请耐心等候":'你的材料已经通过审核了';
            return json(['code'=>200,'status'=>$userUploadMsg->status,'msg'=>$msg]);
        }else if($userUploadMsg->status == MaterialStatusEnum::NO_PASS_BY_AI){
            return json(['code'=>300,'status'=>$userUploadMsg->status,'msg'=>"文章查重不通过,详情请看文件备注和不通过原因",'reason'=>"文件不通过查重，请修改后重新上传。",'audit_url'=>$userUploadMsg->audit_url,'remarks'=>$userUploadMsg->remarks]);
        }
    }
}