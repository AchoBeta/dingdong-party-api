<?php


namespace app\common\validate;


class EditNoteValidate extends BaseValidate
{
    protected $rule = [
        'gender'=>'require|isPositiveInteger|in:1,2',
        'nation' => 'require|isNotEmpty',
        'origin' => 'require|isNotEmpty',
        'id_card' => 'require|isNotEmpty',
        "birth" => 'require|isNotEmpty',
        "mobile" => 'require|isNotEmpty',
        "family_address" =>'require|isNotEmpty',
        "institute" => "require|isNotEmpty",
        "grade" => 'require|between:2016,2030',
        "major" => 'require|isNotEmpty',
        "class" => 'require|isNotEmpty',
        "dormitory_no" => "require|number|isNotEmpty",
        "dormitory_area" => "require|isNotEmpty",
        "email" => "email"
    ];

    protected $message = [
        'gender'=>'性别的值不能为空或其他特殊值',
        'nation' => '民族不能为空',
        'origin' => '籍贯不能为空',
        'id_card' => '身份证不能为空',
        "birth" => '出生日期不能为空',
        "mobile" => '手机号码不能为空',
        "family_address" =>'家庭地址不能为空',
        "institute" => "学院不能为空",
        "grade" => '年级不能为空，且应该在有效范围',
        "major" => '专业不能为空',
        "class" => '班级不能为空',
        "dormitory_no" => "宿舍号不能为空",
        "dormitory_area" => "所属社区不能为空",
        "email" => "邮箱不符合格式要求"
    ];
}