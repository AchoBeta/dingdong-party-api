<?php


namespace app\lib\exception;


class ScoreException extends BaseException
{
    public $code = 500;
    public $msg = '用户成绩操作异常';
    public $errorCode = 60001;
}