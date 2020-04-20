<?php


namespace app\lib\exception;


class StageException extends BaseException
{
    public $code = 500;
    public $msg = '阶段操作异常';
    public $errorCode = 30000;
}