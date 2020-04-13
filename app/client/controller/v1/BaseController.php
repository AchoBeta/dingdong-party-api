<?php


namespace app\client\controller\v1;


use think\facade\Request;

class BaseController
{
    protected $request;
    public function __construct()
    {
        $this->request = Request::instance()->param();
    }
}