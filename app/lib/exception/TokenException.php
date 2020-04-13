<?php


namespace app\lib\exception;


use think\Exception;

class TokenException extends BaseException
{
    public $code = 401;
    public $msg = 'Token已过期或无效Token';
    public $errorCode = 20001;
}