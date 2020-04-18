<?php


namespace app\common\model;


use think\Model;
use think\model\concern\SoftDelete;

class GeneralBranch extends Model
{
    use SoftDelete;
    protected $autoWriteTimestamp = true;
    public function branches()
    {
        return $this->hasMany(Branch::class,'id','general_branch_id');
    }
}