<?php


namespace app\lib\exception;


class ResultException extends BaseException
{
    public $code = 200;
    public $msg = '查询结果为空';
    public $errorCode = 10001;
}