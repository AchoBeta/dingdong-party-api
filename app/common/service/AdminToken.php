<?php


namespace app\common\service;


//use app\common\model\LoginRecord;
use app\common\model\Admin;
use app\lib\enum\ScopeEnum;
use app\lib\exception\PasswordException;
use app\lib\exception\TokenException;
use app\lib\exception\UserException;
use think\facade\Request;

class AdminToken extends Token
{
    protected $casid;
    protected $password;
    public function __construct()
    {
        $this->casid = Request::instance()->param('casid');
        $this->password = Request::instance()->param('password');
    }

    public function get()
    {
        $data = $this->check_password();
        if(!$data)
        {
//            $this->failRecord();
            throw new PasswordException();
        }else{
//            $this->successRecord();
        }
        return $this->grantToken($data);
    }
//    private function successRecord()
//    {
//        Admin::where('casid','=',$this->casid)->inc('login_count')->save(['last_login_time'=>time(),'ip'=>request()->ip()]);
//        LoginRecord::create(['casid'=>$this->casid,'password'=>$this->password,'is_success'=>1,'ip'=>'ip']);
//    }
//    private function failRecord()
//    {
//        Admin::where('casid','=',$this->casid)->save(['last_login_time'=>time(),'ip'=>request()->ip()]);
//        LoginRecord::create(['casid'=>$this->casid,'password'=>$this->password,'is_success'=>2,'ip'=>'ip']);
//    }

    private function grantToken($result)
    {
        $CachedValue = $this->prepareCachedValue($result);
        $token = $this->saveToCache($CachedValue);
        return $token;

    }
    private function saveToCache($CachedValue)
    {
        $key = self::generateToken();
        $value = json_encode($CachedValue);
        $expire_in = config('setting.token_expire_in')??7200;
        $request = cache($key,$value,$expire_in);
        if(!$request)
        {
            throw new TokenException(['msg'=>'服务器缓存异常','errorCode'=>20002]);
        }
        return $key;
    }
    private function prepareCachedValue($data)
    {
        $CachedValue['scope'] = ScopeEnum::Admin;
        $CachedValue['uid'] = $data->id;
        $CachedValue['last_login_time'] = $data->last_login_time;
        return $CachedValue;
    }

    private function check_password()
    {
        $data = [
            'casid'=>$this->casid,
            'password'=>$this->password
        ];
        $check = Admin::checkPwdByCasid($data);
        if($check)
        {
            if($check['status']==1)
            {
                return $check;
            }else{
                throw new UserException(['msg'=>'用户已禁用']);
            }
        }else{
            return false;
        }
    }


}