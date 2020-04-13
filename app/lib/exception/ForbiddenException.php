<?php


namespace app\lib\exception;


class ForbiddenException extends BaseException
{
    public $code = 403;
    public $msg = '无访问权限';
    public $errorCode = 10003;
}