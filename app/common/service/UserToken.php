<?php
namespace app\common\service;

use app\common\model\Weixin;
use app\lib\enum\ScopeEnum;
use app\lib\exception\TokenException;
use app\lib\exception\WechatException;
use think\Exception;
use app\common\model\User as UserModel;

class UserToken extends Token
{
    protected $code;
    protected $wxAppID;
    protected $wxAppSecret;
    protected $wxLoginUrl;
    function __construct($code)
    {
        $this->wxAppID = config('wx.app_id');
        $this->wxAppSecret = config('wx.app_secret');
        $this->wxLoginUrl = sprintf(config('wx.login_url'),
            $this->wxAppID,$this->wxAppSecret,$code);
    }
    public function get()
    {
        $res = curl_get_common($this->wxLoginUrl);
        $wxResult = json_decode($res,true);
        if(empty($wxResult))
        {
            throw new Exception('获取session_key和openid时异常');
        }else{
            $loginFail = array_key_exists('errcode',$wxResult);
            if($loginFail)
            {
                $this->processLoginError($wxResult);
            }else{
                return $this->grantToken($wxResult);
            }
        }
    }
    private function grantToken($wxResult)
    {
//        拿到openid
//        数据库查看此openid是否存在
//        如果存在则不处理，若不存在那么新增一条user记录
//        生成令牌，准备缓存数据，写入缓存(缓存读取速度快，但维护成本高)key：令牌；value：wxResult、uid、scope(权限标志)、
//        把令牌返回客户端
        $openid = $wxResult['openid'];

        $user = Weixin::getByOpenId($openid);
//        if($user)
//        {
//            $uid = $user->id;
//        }else{
//            $uid = $this->newUser($openid);
//        }
        $uid = $user?$user->id:$this->newUser($openid);
        $cachedValue = $this->prepareCachedValue($wxResult,$uid);
        $token = $this->saveToCache($cachedValue);
        return $token;
    }
    private function saveToCache($cachedValue)
    {
        $key = self::generateToken();
        $value = json_encode($cachedValue);
        $expire_in = config('setting.token_expire_in');
        $request = cache($key,$value,$expire_in);
        if(!$request)
        {
            $e = new TokenException(['msg'=>'服务器缓存异常','errorCode'=>20002]);
//            $e->msg = '服务器缓存异常';
//            $e->errorCode = 10005;
            throw $e;
        }
        return $key;
    }
    private function prepareCachedValue($wxResult,$uid)
    {
        $model = UserModel::find($uid);
        $CachedValue['general_branch_id'] = $model->getData('general_branch_id')??null;
        $CachedValue = $wxResult;
        $CachedValue['uid'] = $uid;
        $CachedValue['scope'] = ScopeEnum::User;
        return $CachedValue;
    }
    private function newUser($openid)
    {
        $user = Weixin::create([
            'openId'=>$openid,
            'last_login_ip'=>request()->ip()
        ]);
        return $user->id;
    }
    private function processLoginError($wxResult)
    {
        $e = new WechatException(['msg'=>$wxResult['errmsg'],'errorCode'=>$wxResult['errcode']]);
//        $e->msg = $wxResult['errmsg'];
//        $e->errorCode = $wxResult['errcode'];
        throw $e;
    }
}