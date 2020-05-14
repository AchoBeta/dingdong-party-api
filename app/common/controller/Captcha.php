<?php


namespace app\common\controller;


class Captcha
{
    public static function recognize($image){

        $res = imagecreatefromstring($image);
        $rgbArray=array();
        $wid= 70-1;
        $hid= 30-1;
        for ($i = 1;$i < $hid;++$i){
            for ($j=1;$j<$wid;++$j){
                $rgb=imagecolorat($res,$j,$i);
                $rgbArray[$i][$j]=imagecolorsforindex($res,$rgb);
            }
        }

        $data = [];

//二值化
        for ($i = 1; $i < $hid; $i ++) {
            for ($j = 1; $j < $wid; $j ++) {
                // if ($rgbArray[$i][$j]['red'] < 210 && $rgbArray[$i][$j]['red'] >= 180) {
                //     echo '□';
                // } else {
                //     echo '■';
                if ($rgbArray[$i][$j]['red'] < 126 /*&& $rgbarray[$i][$j]['green']<126*//*|| $rgbarray[$i][$j]['blue'] < 125*/) {
                    $data[$i][$j] = 1;
                    //echo '■';
                } else {
                    $data[$i][$j] = 0;
                    //echo '□';
                }
            }
            // echo "<br>";
        }
        // echo("<br/>除噪后:<br/>");
//除噪
        for ($i = 1; $i < $hid; $i ++) {
            for ($j = 1; $j < $wid; $j ++) {
                $num = 0;
                if($data[$i][$j] == 1)
                {
                    // 上
                    if(isset($data[$i-1][$j])){
                        $num = $num + $data[$i-1][$j];
                    }
                    // 下
                    if(isset($data[$i+1][$j])){
                        $num = $num + $data[$i+1][$j];
                    }
                    // 左
                    if(isset($data[$i][$j-1])){
                        $num = $num + $data[$i][$j-1];
                    }
                    // 右
                    if(isset($data[$i][$j+1])){
                        $num = $num + $data[$i][$j+1];
                    }
                    // 上左
                    if(isset($data[$i-1][$j-1])){
                        $num = $num + $data[$i-1][$j-1];
                    }
                    // 上右
                    if(isset($data[$i-1][$j+1])){
                        $num = $num + $data[$i-1][$j+1];
                    }
                    // 下左
                    if(isset($data[$i+1][$j-1])){
                        $num = $num + $data[$i+1][$j-1];
                    }
                    // 下右
                    if(isset($data[$i+1][$j+1])){
                        $num = $num + $data[$i+1][$j+1];
                    }
                }
                if($num == 0){
                    $data[$i][$j] = 0;
                }
            }
        }
        /*
                for ($i = 5; $i < $hid-2; $i ++) {
                    for ($j = 1; $j < $wid; $j ++) {
                        if ($data[$i][$j] == 1)
                            echo '■';
                        elseif($data[$i][$j] == 0)
                            echo '□';
                    }
                    echo "<br>";
                }
        */
//切分
        $Vword[0] ='';
        $Vword[1] ='';
        $Vword[2] ='';
        $Vword[3] ='';

        for ($i = 1; $i < 28; $i ++) {
            for ($j = 1; $j < 69; $j ++) {
                if ($j >= 7 && $j <= 17) {
                    $Vword[0] .= $data[$i][$j];
                }
                elseif ($j >= 21 && $j <= 31) {
                    $Vword[1] .= $data[$i][$j];
                }
                elseif ($j >= 36 && $j <= 46) {
                    $Vword[2] .= $data[$i][$j];
                }
                elseif ($j >= 52 && $j <= 62) {
                    $Vword[3] .= $data[$i][$j];
                }
            }
        }

        $dic = array(
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000111100000011001100001100001100011000011001110000111011100001110111000011101110000111011100001110111000011101110000111011100001110011000011000110000110000110011000000111100000000000000000000000000000000000000000000000' => 0,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000011100000011111000001111110000000011100000000111000000001110000000011100000000111000000001110000000011100000000111000000001110000000011100000000111000001111111100000000000000000000000000000000000000000000000' => 1,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111000000111111000011111111000100011110010000011100000000111000000001100000000011000000001100000000011000000001100000000110000000001000001000111111110011111111001111111110000000000000000000000000000000000000000000000' => 2,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111100000011111100011001111000000001110000000011000000001110000000111110000000111100000000111100000000111000000001110000000011101100000110011110011100111111100000111110000000000000000000000000000000000000000000000000' => 3,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000000000110000000011100000001111000000101110000010011100000100111000010001110001000011100100000111001111111111011111111110000000111000000001110000000011100000000111000000000000000000000000000000000000000000000' => 4,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111111000111111110001110111000100000000001111000000011111000001111111010000001111100000000111100000000111000000000110000000001100000000011011000000100111100000000111111000000000000000000000000000000000000000000000000' => 5,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000000111000001111000000111100000011100000001111000000011100000000111111100011100011100111000011001110000111011100001110111000011100110000111001110001100001100111000001111000000000000000000000000000000000000000000000000' => 6,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000111111111101111111111011111111101100001011010000010110000000011000000000110000000011000000000110000000001100000000111000000001100000000011000000001100000000011000000000110000000000000000000000000000000000000000000000000' => 7,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000111110000111001110011100001110111000011101111000111011111001100011111110000011111100000111111100011001111101110001111011100001110111000011101110000111001110011100001111100000000000000000000000000000000000000000000000' => 8,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111000001110011000011000111001110000111011100001110111000011101110000111011100001110011100011100011111110000000011100000000111000000011100000001110000001110000001110000000000000000000000000000000000000000000000000000' => 9,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000000111000001111000000111100000011100000001111000000011100000000111111100011110011100111000011101110000111011100001110111000011100110000111001110001100001100111000001111000000000000000000000000000000000000000000000000' => 6,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000011110000001111110000111111110001000111100000000111000000001110000000011000000000110000000011000000000110000000011000000001100000000010000011001111111100111111110001111111100000000000000000000000000000000000000000000000' => 2,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000111110000111000110011000000110111000011101110000111011111001100011011110000011101100000111111100011001011101110001111011100001010110000011101110000101001010011100001111100000000000000000000000000000000000000000000000' => 6,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001010000000110100000011101000000001110000000000100000000101000000001010000000010100000000011000000001010000000001000000000111000000000110000000010100000011010110000000000000000000000000000000000000000000000' => 1,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111000000110011000011000011000110000110011100001110111000011101110000111011100001110111000011101110000111011100001110111000011100110000110001100001100001100110000001111000000000000000000000000000000000000000000000000' => 0,
            // '000000000000000000000000000000000000000000000000000000000000000000000000000000000111110000111000110011000000110111000011101110000111011111001100011011110000011101100000111111100011001011101110001111011100001010110000011101110000101001010011100001111100000000000000000000000000000000000000000000000' => 8,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111111000111111110001111110000100000000001111000000011111100001111111110000001111100000000111100000000111000000000110000000001100000000011011100000100110100010000111111000000000000000000000000000000000000000000000000' => 5,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000011111111100111111111001111111110110000001101000000011000000001100000000011000000001100000000011000000000110000000011000000000110000000001100000000110000000001100000000011000000000000000000000000000000000000000000000000' => 7,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111100001110011100111000011101110000111011110001110111111011000111111100000111111100001111111000110011111011100011110111000011101110000111011100001110011100111000011111000000000000000000000000000000000000000000000000' => 8,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000001111111000111111110001111111000100000000001111000000011111100001111111110000001111100000000111100000000111000000000110000000011100000000011011100000100111100010000111111000000000000000000000000000000000000000000000000' => 5,
            '000000000000000000000000000000000000000000000000000000000000000000000000000000000000011100000111100000011110000001110000000111100000001110000000011111110001111001110011100001110111000011001110000111011100001110011000011100111000110000110011100000111100000000000000000000000000000000000000000000000' => 6
        );
        $result = [];
        for($tmp_count=0;$tmp_count<4;$tmp_count++){
            $result[$tmp_count]['similar'] = 0;
            foreach($dic as $key=>$value){
                similar_text($Vword[$tmp_count],$key,$similarResult);
                if($similarResult>$result[$tmp_count]['similar']){
                    $result[$tmp_count]['similar'] = $similarResult;
                    $result[$tmp_count]['num'] = $value;
                }
            }
        }
        $captcha = $result[0]['num'].$result[1]['num'].$result[2]['num'].$result[3]['num'];

        //echo("验证码为:$captcha<br/>");

        //echo("1:".$result[0]['num']."<br/>2:". $result[1]['num']."<br/>3:". $result[2]['num']."<br/>4:".$result[3]['num']);

        return $captcha;
    }
}