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
        CheckLoginCount::class=>['only'=>['adminToken']]
    ];
    public function getToken($code = '')
    {
        (new TokenGet())->goCheck('user');
        $ut = new UserToken($code);
        $token = $ut->get();
        return json([
            'token'=>$token
        ]);
    }
    public function adminToken()
    {
        (new TokenGet())->goCheck('admin');
        $at = new AdminToken();
        $token = $at->get();
        return json([
            'token'=>$token
        ]);
    }
    public function logout()
    {
        $token = request()->header('token');
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
}