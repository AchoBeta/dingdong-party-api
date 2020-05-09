<?php


namespace app\party\controller\v1;

use app\common\model\Stage as StageModel;
use app\common\service\Token;
use app\lib\exception\StageException;


class Stage
{
    /**
     * Notes:获取阶段列表
     * User: charl
     * Date: 2020/4/19
     * Time: 23:16
     */
    public function index()
    {
        $res = StageModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->with(['task'])
            ->select();
        return json($res);
    }

    /**
     * Notes:获取指定数据
     * User: charl
     * Date: 2020/4/19
     * Time: 23:42
     */
    public function read($id)
    {
        $res = StageModel::where('general_branch_id',Token::getCurrentTokenVar('general_branch_id'))
            ->with(['user_branch'])
            ->withCount(['user_state' => 'total'])
            ->find($id);
        return json($res);
    }

    /**
     * Notes:新增数据项
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     */
    public function save()
    {
        $res = StageModel::create(input());
        if($res)
        {
            return $res;
        }else{
            throw new StageException(['msg'=>'阶段创建错误','errorCode'=>30001]);
        }
    }

    /**
     * Notes:更新
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     */
    public function update($id)
    {
        $stage = StageModel::find($id);
        $res = $stage->save(input());
        if($res)
        {
            return $res;
        }else{
            throw new StageException(['msg'=>'阶段更新错误','errorCode'=>30002]);
        }
    }
    /**
     * Notes:删除
     * User: charl
     * Date: 2020/4/19
     * Time: 23:43
     */
    public function delete($id)
    {
        $stage = StageModel::find($id);
        $res = $stage->together(['task'])->delete();
        if($res)
        {
            return $res;
        }else{
            throw new StageException(['msg'=>'阶段删除错误','errorCode'=>30003]);
        }
    }
}