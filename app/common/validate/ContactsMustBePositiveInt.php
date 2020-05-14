<?php


namespace app\common\validate;


class ContactsMustBePositiveInt extends BaseValidate
{
    protected $rule = [
        'develop_contact_first'=>'require|isPositiveInteger|isNotEmpty',
        'develop_contact_second'=>'require|isPositiveInteger|isNotEmpty',
        'recommend_first'=>'require|isPositiveInteger|isNotEmpty',
        'recommend_second'=>'require|isPositiveInteger|isNotEmpty',
    ];

    protected $message = [
        'develop_contact_first'=>'第一个联系人不能为空',
        'develop_contact_second' => '第二个联系人不能为空',
        'recommend_first'=>'第一个联系人不能为空',
        'recommend_second'=>'第二个联系人不能为空',
    ];

    protected $scene = [
        'train'=>['develop_contact_first','develop_contact_second'],
        'introduce' => ['recommend_first','recommend_second'],
    ];
}