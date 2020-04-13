<?php


namespace app\common\validate;


use app\lib\exception\ParameterException;
use think\Exception;
use think\facade\Request;
use think\Validate;


class BaseValidate extends Validate
{
    public function goCheck($scene='')
    {
        $param = Request::instance()->param();
        $result = $scene==''?$this->check($param):$this->scene($scene)->check($param);
        if(!$result)
        {
            $e = new ParameterException(['msg'=>$this->error]);
//            $e->msg = $this->error;
            throw $e;
        }else{
            return true;
        }
    }
    protected function isPositiveInteger($value,$rule = '',$data = '',$field = '')
    {
        if(is_numeric($value)&&is_int($value+0)&&($value+0)>0)
        {
            return true;
        }else{
            return $field.' must be Int and more then zero';
        }
    }
    protected function isNotEmpty($value,$rule = '',$data = '',$field = '')
    {
        if(empty($value))
        {
//            return $field.'不允许为空';
            return false;
        }else{
            return true;
        }
    }
    protected function isLimitNumber($value,$rule = '',$data = '',$field = '')
    {
        if(is_numeric($value)&&is_int($value+0)&&($value+0)>0&&($value+0)<=50){
            return true;
        }else{
            return $field.' must be int and between 1 and 50.';
        }
    }
    public function getDataByRule(array $arrays,$scene = '')
    {
        if(array_key_exists('user_id',$arrays)|array_key_exists('uid',$arrays))
        {
            throw new ParameterException(['msg'=>'参数中包含非法参数名']);
        }
        $newArray = [];
        foreach ($this->scene[$scene] as $key=>$value)
        {
//            $newArray[$key] = $arrays[$key];
            if(isset($arrays[$value]))
            {
                $newArray[$value] = $arrays[$value];
            }
        }
        return $newArray;
    }
}