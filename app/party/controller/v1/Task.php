<?php


namespace app\party\controller\v1;

use app\common\model\Task as TaskModel;
use app\common\service\Token;
use app\lib\exception\TaskException;


class Task
{
    /**
     * Notes:获取任务列表
     * User: charl
     * Date: 2020/4/19
     * Time: 23:16
     */
    public function index()
    {
        $res = TaskModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->withCount(['user_branch'=>'user_count'])
            ->select();
        return json($res);
    }

    /**
     * Notes:获取指定任务数据
     * User: charl
     * Date: 2020/4/19
     * Time: 23:42
     * @param $id
     * @return array|\think\Model|null
     * @throws \app\lib\exception\TokenException
     * @throws \think\Exception
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function read($id)
    {
        $res = TaskModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->with(['user'=>function($query){
                $query->hidden(['pivot'])->with(['general_branch'=>function($query){
                    $query->visible(['name','general_branch_secretary']);
                }]);
            }])
            ->withCount(['user_branch' => 'total'])
            ->find($id);
        return json($res);
    }

    /**
     * Notes:新增任务数据项
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     */
    public function save()
    {
        $res = TaskModel::create(input());
        if($res)
        {
            return json($res);
        }else{
            throw new TaskException(['msg'=>'任务创建错误','errorCode'=>40001]);
        }
    }

    /**
     * Notes:更新任务
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     * @param $id
     * @return bool
     * @throws TaskException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function update($id)
    {
        $task = TaskModel::find($id);
        $res = $task->save(input());
        if($res)
        {
            return $res;
        }else{
            throw new TaskException(['msg'=>'任务更新错误','errorCode'=>40002]);
        }
    }

    /**
     * Notes:删除任务
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     * @param $id
     * @return bool
     * @throws TaskException
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public function delete($id)
    {
        $task = TaskModel::find($id);
        $res = $task->delete();
        if($res)
        {
            return $res;
        }else{
            throw new TaskException(['msg'=>'任务删除错误','errorCode'=>40003]);
        }
    }
}