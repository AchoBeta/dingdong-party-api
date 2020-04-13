<?php
// 应用公共文件
use Curl\Curl;

function curl_get_common($url, &$httpCode = 0)
{
    $curl = new Curl();
    $curl->setopt(CURLOPT_SSL_VERIFYPEER, false);
    $curl->get($url);
//    $curl->close();
    return $curl->response;
}
function getRandChar($length)
{
    $str = null;
    $strPol = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz';
    $max = strlen($strPol) - 1;
    for ($i=0;$i<$length;$i++)
    {
        $str.=$strPol[rand(0,$max)];
    }
    return $str;
}
//保留指定key以及value，过滤其它
function filter_myArray(array $field,array $originArray)
{
    foreach ($originArray as $k=>$v)
    {
        if (!in_array($k,$field))
        {
            unset($originArray[$k]);
        }else{
            if(is_array($originArray[$k]))
            {
                unset($originArray[$k]);
            }
        }
    }
    return $originArray;
}
function combine_to_createTime(array $originArray)
{
    if($originArray['start_date']??null&&$originArray['end_date']??null)
    {
        $originArray['create_time'] = [$originArray['start_date'],$originArray['end_date']];
        unset($originArray['start_date']);
        unset($originArray['end_date']);
    }
    return $originArray;
}