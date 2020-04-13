<?php


namespace app\lib\exception;


class PasswordException extends BaseException
{
    public $code = 400;
    public $msg = '密码不正确';
    public $errorCode = 10004;
}