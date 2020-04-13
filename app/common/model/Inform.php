<?php
declare (strict_types = 1);

namespace app\api\model;


use think\Model;
use think\model\concern\SoftDelete;


class Inform extends Model
{
    use SoftDelete;
    protected $deleteTime = 'delete_time';
    protected $pk = 'Id';
    protected $hidden = [
        'pivot'
    ];
    public function item()
    {
        return $this->hasMany(InformItem::class,'inform_id','Id');
    }
    public function labels()
    {
        return $this->belongsToMany(Label::class,InformLabel::class);
    }
    public function type()
    {
        return $this->belongsTo(InformType::class,'type_id','Id');
    }
    public function searchCreateTimeAttr($query, $value, $data)
    {
        $query->whereBetweenTime('create_time', $value[0], $value[1]);
    }
    public function searchIsPublishAttr($query, $value, $data)
    {
        $query->where('is_publish','=',$value);
    }
    public function getIsPublishAttr($value)
    {
        $list = [
            1=>'未发布',
            2=>'已发布'
        ];
        return $list[$value];
    }
    public function setDeadlineAttr($value)
    {
        return strtotime($value);
    }
    public function getDeadlineAttr($value)
    {
        return date('Y-m-d H:i:s', (int)$value);
    }
    public function setExpiresAttr($value)
    {
        return strtotime($value);
    }
    public function getExpiresAttr($value)
    {
        return date('Y-m-d H:i:s', (int)$value);
    }
    public static function getInformByTypeId($id)
    {
        $res = self::where('type_id','=',$id)->select();
        return $res;
    }
    public function setStatusAttr($value)
    {
        return $value?$value:1;
    }

    /**
     * Notes:模型内静态简单业务
     * Admin: charl
     * Date: 2020/1/26
     * Time: 23:23
     * @param *
     * @return \think\Collection
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\DbException
     * @throws \think\db\exception\ModelNotFoundException
     */
    public static function getInformByIsPublish($is_publish)
    {
        $res = self::where('is_publish','=',$is_publish)->select();
        return $res;
    }
    public static function getInformByStatus($status)
    {
        $res = self::where('is_publish','=',$status)->select();
        return $res;
    }
    public static function deleteInform($id)
    {
        $inform = self::find($id);
        if($inform)
        {
            return $inform->delete();
        }else{
            return null;
        }
    }
    public static function restoreInform($id)
    {
        $inform = self::onlyTrashed()->find($id);
        return $inform?($inform->restore()):null;
    }
    public static function searchInform($data)
    {
        $limit = $data['limit']??10;
        $field = ['is_publish','start_date','end_date'];
        $data = filter_myArray($field,$data);
//        if(array_key_exists('start_date',$data)&&array_key_exists('end_date',$data))
        if($data['start_date']??null&&$data['end_date']??null)
        {
            $data['create_time'] = [$data['start_date'],$data['end_date']];
            unset($data['start_date']);
            unset($data['end_date']);
        }
        $keys = array_keys($data);
        $res = self::withSearch($keys,$data)->order(['create_time'=>'desc'])->paginate($limit);
        return $res;
    }
    public static function getInformWithItems($id)
    {
        $items = self::where('id',$id)->with('item')->find();
        return $items['item'];
    }
    public static function getWithLabelById($id)
    {
        $res = self::with([
            'labels'=>function($query){
                $query->order(['create_time'=>'desc']);
            }
        ])->find($id);
        return $res;
    }

}