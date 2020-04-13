<?php


namespace app\lib\http;


use app\api\model\LoginRecord;
use app\lib\exception\TokenException;

class CheckLoginCount
{
    public function handle($request,\Closure $next)
    {
        $ip = request()->ip();
        if(LoginRecord::checkIpCount($ip)>config('setting.limit_login_count'))
        {
            throw new TokenException(['msg'=>'检测到您的登陆操作异常，请稍后重试','errorCode'=>20010]);
        }
        return $next($request);

    }
}