<?php


namespace app\client\controller\v1;
use app\common\model\Task as TaskModel;
use app\common\validate\IDMustBePostiveInt;
use think\response\Json;

class Task extends BaseController
{
    /**
     * Notes: 获取所有任务
     * User: hua-bang
     * Date: 2020/4/18
     * Time: 1:00
     * @return Json
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function getAllTasks(){
        $allTasks = TaskModel::select();
        return json($allTasks);
    }

    /**
     * Notes: 根据id获取所有任务
     * User: hua-bang
     * Date: 2020/4/18
     * Time: 1:05
     * @param $id //任务号
     * @return array|\think\Model|null
     */
    public function getTaskById($id){
        (new IDMustBePostiveInt())->goCheck();
        return json(TaskModel::where('id',$id)->find());
    }

    /**
     * Notes:
     * User: hua-bang
     * Date: 2020/4/18
     * Time: 1:12
     * @param $id
     * @return array|\think\Model|null
     */
    public function getNextTask($id){
        return json(TaskModel::getNextById($id));
    }

    public function getTasksByStage($stageId){
        return json(TaskModel::where('stage_id',$stageId)->order('order')->select());
    }

}