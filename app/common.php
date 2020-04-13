<?php
// 应用公共文件
use Curl\Curl;

function curl_get($url, &$httpCode = 0)
{
//    $ch = curl_init();
//    curl_setopt($ch,CURLORT_URL,$url);
//    curl_setopt($ch,CURLOPT_RETURNTRANSFER,1);
//
//    curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
//    curl_setopt($ch,CURLOPT_CONNECTTIMEOUT,10);
//    $file_contents = curl_exec($ch);
//    $httpCode = curl_getinfo($ch,CURLINFO_HTTP_CODE);
//    curl_close($ch);
    $curl = new Curl();
    $curl->get($url);
//    $curl->close();
    return $curl->response;
}