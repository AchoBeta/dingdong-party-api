<?php


namespace app\lib\exception;


class ContactsException extends BaseException
{
    public $code = 301;
    public $msg = '选择联系人出错';
    public $errorCode = 10003;
}