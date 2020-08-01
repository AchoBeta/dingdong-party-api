<?php
namespace app\lib\exception;
use think\Exception;
use Throwable;

class BaseException extends Exception
{
    public $code = 500;
    public $msg = '基础错误';
    public $errorCode = 10000;
    public $data = [];
    public function __construct($params = [])
    {
        if(!is_array($params))
        {
            return ;
        }
        if(array_key_exists('code',$params))
        {
            $this->code = $params['code'];
        }
        if(array_key_exists('msg',$params))
        {
            $this->msg = $params['msg'];
        }
        if(array_key_exists('errorCode',$params))
        {
            $this->errorCode = $params['errorCode'];
        }
        if(array_key_exists('data',$params))
        {
            $this->errorCode = $params['data'];
        }
    }
}