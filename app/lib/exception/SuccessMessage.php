<?php


namespace app\lib\exception;


class SuccessMessage extends BaseException
{
    public $code = 201;
    public $msg = 'success';
    public $errorCode = 0;
}