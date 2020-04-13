<?php


namespace app\common\service;

use app\lib\exception\TokenException;
use think\Exception;
use think\facade\Cache;
use think\facade\Request;

class Token
{
    protected static function generateToken()
    {
        //common.php中的getRandChar()
        $randChars = getRandChar(32);
        //拼接加密
        $timestamp = Request::server('REQUEST_TIME_FLOAT');//$_SERVER('REQUEST_TIME_FLOAT');
        $salt = config('secure.token_salt');
        return md5($randChars.$timestamp.$salt);
    }

    public static function getCurrentTokenVar($key)
    {
        $token = Request::instance()->header('token');
        $vars = cache($token);
        if(!$vars)
        {
            throw new TokenException();
        }else{
            if(!is_array($vars))
            {
                $vars = json_decode($vars,true);
            }
            if(array_key_exists($key,$vars))
            {
                return $vars[$key];
            }else{
                throw new Exception('尝试获取指定Token变量但不存在');
            }
        }

    }

    public static function getCurrentUid()
    {
        $uid = self::getCurrentTokenVar('uid');
        return $uid;
    }


}