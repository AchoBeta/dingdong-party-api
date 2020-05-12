<?php


namespace app\client\controller\v1;
use app\common\model\UserDetail;
use app\common\model\UserState;
use app\common\model\Weixin;
use app\common\model\User as UserModel;
use app\common\validate\TaskIDMustBePositiveInt;
use app\lib\enum\MaterialStatusEnum;
use app\lib\enum\TaskNeedMaterialEnum;
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
        if($taskIdFromSql!=$taskId){
            throw new TaskException(['msg'=>'任务不属于用户当前阶段']);
        }
        $needCheck = in_array($taskId,TaskNeedMaterialEnum::TASK_NEED_CHECK);
        $res = $this->upload($taskId,$needCheck);
        return json($res);
    }

    protected function upload($taskId,$needCheck=false){
        $uid = TokenService::getCurrentUid();
        $userUploadMsg = UserDetail::where(['user_id'=>$uid,"task_id"=>$taskId])->find();
        $hasUpload = $userUploadMsg?true:false;
        if($hasUpload){
            $casId = $userUploadMsg->casid;
            if($needCheck){
                if($userUploadMsg->status >= MaterialStatusEnum::PASS_BY_AI){
                    $msg = $userUploadMsg->status==MaterialStatusEnum::PASS_BY_AI?"你的材料正处于管理员审核阶段，请耐心等候":'你的材料已经通过审核了，请勿重复提交.';
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
            $openId = Weixin::where('id',$uid)->find()->value('openId');
            $casId = UserModel::where('openId',$openId)->find()->value('casId');
            $userUploadMsg = [
                'user_id' => $uid,
                'casid' => $casId,
                'task_id' => $taskId
            ];
        }
        $file = request()->file();
        try{
            validate(['file'=>'filesize:3145728|fileExt:doc,docx'])
                ->check($file);
        }catch (ValidateException $e){
            echo $e->getMessage();
        }
        $rule = function () use ($taskId){
            return TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];
        };
        $saveName = \think\facade\Filesystem::disk('public')->putFile( "stuWord/$casId", $file['file'],$rule);
        if($needCheck){
            $path = "C:/inetpub/wwwroot/Party/public/storage/".$saveName;
            $content = $this->getFileStr($path);
            $result = $this->checkOriginal($content);
            $res = $result>self::PASS_SCORE?true:false;
        }else{
            $res = true;
        }
        $status = $needCheck?($res?MaterialStatusEnum::PASS_BY_AI:MaterialStatusEnum::NO_PASS_BY_AI):MaterialStatusEnum::NO_CHECKED;
        $msg = $needCheck?($status==MaterialStatusEnum::PASS_BY_AI?"通过自动查重审核，等待管理员审核":"自动查重审核不通过，请检查文章的原创性"):"文件上传成功，等待管理员审核";
        if($hasUpload){
            $userUploadMsg->material_name = TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];;
            $userUploadMsg->status = $status;
            $userUploadMsg->url = $saveName;
            $userUploadMsg->save();
        }else{
            $userUploadMsg['material_name'] = TaskNeedMaterialEnum::TASK_NAME_ARR[$taskId];;
            $userUploadMsg['status'] = $status;
            $userUploadMsg['url'] = $saveName;
            $insertSuccess = UserDetail::insert($userUploadMsg);
            $msg = $insertSuccess?$msg:"上传失败，请重试";
        }
        $sentence = $res?"":$result['result']['sentence'];
        $code = $res ? 200:300;
        $finalRes = $needCheck?(['msg'=>$msg,'code'=>$code,'status'=>$status,'score'=>$result['result']['score'],'sentence'=>$sentence]):(['msg'=>$msg,'status'=>$status,'code'=>$code]);
        return $finalRes;
    }

    protected function getFileStr($path){
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
        $templateProcessor->saveAs(time()."$casId.docx");
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
}