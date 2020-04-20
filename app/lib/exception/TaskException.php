<?php


namespace app\lib\exception;


class TaskException extends BaseException
{
    public $code = 500;
    public $msg = '任务操作异常';
    public $errorCode = 40000;
}