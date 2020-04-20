<?php


namespace app\lib\exception;


class UserAddException extends BaseException
{
    public $code = 301;
    public $msg = '用户添加出现异常';
    public $errorCode = 10002;
}