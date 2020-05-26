<?php


namespace app\lib\exception;


class FileException extends BaseException
{
    public $code = 500;
    public $msg = '必须上传文件。';
    public $errorCode = 80000;
}