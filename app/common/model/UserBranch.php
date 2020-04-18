<?php


namespace app\common\model;


use think\Model;
use think\model\concern\SoftDelete;

class UserBranch extends Model
{
    use SoftDelete;
    protected $autoWriteTimestamp = true;
}