<?php


namespace app\common\model;


use think\Model;

class GeneralBranch extends Model
{
    public function branches()
    {
        return $this->hasMany(Branch::class,'id','general_branch_id');
    }
}