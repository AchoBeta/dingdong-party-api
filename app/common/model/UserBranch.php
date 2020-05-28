<?php


namespace app\common\model;


use think\Model;
use think\model\concern\SoftDelete;

class UserBranch extends Model
{
    use SoftDelete;
    protected $autoWriteTimestamp = true;
    protected $json = ['investigation_in_half_year_activist','opinions_for_supply_activist','opinions_for_supply_prepare','application_info'];
    public function firstContact(){
        return $this->hasMany(Contacts::class,'id',"develop_contact_first");
    }
    public function secondContact(){
        return $this->hasMany(Contacts::class,'id',"develop_contact_second");
    }
}