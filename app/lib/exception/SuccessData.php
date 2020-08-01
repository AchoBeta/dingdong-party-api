<?php


namespace app\lib\exception;


class SuccessData extends BaseException
{
    public $code = 200;
    public $msg = 'success';
    public $errorCode = 0;
    public $data = [];
}