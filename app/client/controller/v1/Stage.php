<?php


namespace app\client\controller\v1;
use app\common\model\Stage as StageModel;
use app\common\service\Token as TokenService;
use app\common\validate\IDMustBePostiveInt;

class Stage extends BaseController
{
    public function getStageType(){
        return json(StageModel::select()->column('name'));
    }

    public function getAllStages(){
        return json(StageModel::select());
    }

    public function getStageById($id){
        (new IDMustBePostiveInt())->goCheck();
        return json(StageModel::where('id',$id)->find());
    }

    public function getNextStage($id){
        (new IDMustBePostiveInt())->goCheck();
        return json(StageModel::getNextOrder($id));
    }

    public function getAllStagesWithTasks(){
        $res = StageModel::with('task')->select();
        return json($res);
    }

}