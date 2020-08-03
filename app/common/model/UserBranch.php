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
        return $this->belongsTo(Contacts::class,'develop_contact_first_id');
    }
    public function secondContact(){
        return $this->belongsTo(Contacts::class,'develop_contact_second_id');
    }

    public function firstRecommendContact(){
        return $this->hasOne(Contacts::class,'id',"recommend_first");
    }
    public function secondRecommendContact(){
        return $this->hasOne(Contacts::class,'id',"recommend_first");
    }
}