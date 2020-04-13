<?php


namespace app\lib\exception;


class ParameterException extends BaseException
{
    public $code = 400;
    public $msg = '参数有误';
    public $errorCode = 10001;
}