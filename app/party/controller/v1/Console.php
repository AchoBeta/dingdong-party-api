<?php


namespace app\party\controller\v1;


use app\lib\exception\SuccessData;

class Console
{
    public function index()
    {
        $stage = \app\common\model\Stage::withCount(['user_state' => 'total'])->select();
        throw new SuccessData(['data'=>$stage]);
    }
}