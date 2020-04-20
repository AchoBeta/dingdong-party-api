<?php


namespace app\client\service;
use app\common\model\Weixin;
use app\common\service\Token as TokenService;
use app\lib\exception\UserException;
use app\lib\weChat\WXBizDataCrypt;

class UserBind
{
    public function bindUserInfo($encryptedData, $iv){
        $sessionKey = TokenService::getCurrentTokenVar('session_key');
        $uid = TokenService::getCurrentUid();
        $openId = TokenService::getCurrentTokenVar('openid');
        $appId = config("wx.app_id");
        $pc = new WXBizDataCrypt($appId, $sessionKey);
        $errCode = $pc->decryptData($encryptedData, $iv, $data );

        if ($errCode == 0) {
            $dataArr = json_decode($data,true);
            if($dataArr['openId'] != $openId)
                throw new UserException(['msg'=>'用户微信身份不匹配','errorCode'=>10008]);
            else{
                $dataArr['id'] = $uid;
                $res = Weixin::where('openId','=',$openId)->save($dataArr);
            }

        } else {
            print($errCode . "\n");
        }
    }
}