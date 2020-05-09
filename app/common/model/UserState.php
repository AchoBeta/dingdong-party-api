<?php


namespace app\common\model;


use app\common\service\Token;
use think\Model;

class UserState extends Model
{
    protected $autoWriteTimestamp = true;
    public function onBeforeInsert($model)
    {
        $model->setAttr('general_branch_id',Token::getCurrentTokenVar('general_branch_id'));
    }
    public function user_branch()
    {
        return $this->belongsTo('user_branch','casid','id');
    }

}