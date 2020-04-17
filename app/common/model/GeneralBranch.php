<?php


namespace app\common\model;


use think\Model;

class GeneralBranch extends Model
{
    protected $autoWriteTimestamp = true;
    public function branches()
    {
        return $this->hasMany(Branch::class,'id','general_branch_id');
    }
}