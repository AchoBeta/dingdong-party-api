<?php


namespace app\lib\exception;


class AdminException extends BaseException
{
    public $code = 500;
    public $msg = '管理员操作异常';
    public $errorCode = 80000;
}