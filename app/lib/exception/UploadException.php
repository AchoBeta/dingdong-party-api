<?php


namespace app\lib\exception;


class UploadException extends BaseException
{
    public $code = 500;
    public $msg = '上传异常';
    public $errorCode = 20001;
}