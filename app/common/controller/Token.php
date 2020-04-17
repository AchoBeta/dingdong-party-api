<?php


namespace app\common\controller;
use app\lib\http\CheckLoginCount;
use app\common\service\AdminToken;
use app\common\service\UserToken;
use app\common\validate\TokenGet;
use app\lib\exception\SuccessMessage;
use app\lib\exception\TokenException;
use Exception;

class Token
{
    protected $middleware = [
//        CheckLoginCount::class=>['only'=>['adminToken']]
    ];
    public function getToken($code = '')
    {
        (new TokenGet())->goCheck('user');
        $ut = new UserToken($code);
        $token = $ut->get();
        return json([
            'Authorization'=>$token
        ]);
    }
    public function adminToken()
    {
        (new TokenGet())->goCheck('admin');
        $at = new AdminToken();
        $token = $at->get();
        return json([
            'Authorization'=>$token
        ]);
    }
    public function logout()
    {
        $token = request()->header('Authorization');
        if(!$token)
        {
            throw new TokenException(['msg'=>'token缺失']);
        }
        if(!cache($token))
        {
            throw new TokenException();
        }
        $res = cache($token,NULL);
        if($res)
        {
            throw new SuccessMessage();
        }else{
            throw new Exception('token撤销失败');
        }
    }
    public function checkLogin()
    {
        $token = request()->header('Authorization');
        if(!$token)
        {
            throw new TokenException(['msg'=>'Authorization缺失']);
        }
        if(!cache($token))
        {
            throw new TokenException(['msg'=>'登陆已过期或尚未登陆']);
        }else{
            throw new SuccessMessage(['msg'=>'登陆状态正常']);
        }
    }
}