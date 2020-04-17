<?php


namespace app\party\controller\v1;

use app\common\model\User as UserModel;
use app\common\service\Token;

class User
{
    public function index()
    {
        $list = UserModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->select();
        return $list;
    }

    public function read($id)
    {
        $list = UserModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->find($id);
        return $list;
    }

    public function save()
    {

    }

}