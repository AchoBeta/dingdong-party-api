<?php


namespace app\common\controller;


use GuzzleHttp\Client;
use GuzzleHttp\Cookie\CookieJar;

class Thesis
{
    protected $client;
    protected $jar;

    function __construct()
    {
        $this->jar = new CookieJar();
        $this->client = new Client();
    }


    //这个是废弃的接口
//    public function getVerifyImg(){
//        $url = "http://www.zhipaiwu.com/index.php/Index/verify.html";
//        $res = $this->client->request('GET',$url,[
//            'cookies' => $this->jar
//        ]);
//        $img = $res->getBody();
//        $sessionId = $res->getHeaders()['Set-Cookie'][0];
//        dump($res->getHeaders()['Set-Cookie']);
//        dump($sessionId);
//        $reg = "/PHPSESSID=.+.;/";
//        dump(preg_match($reg,$sessionId,$newRes));
//        $sessionId = str_replace(["PHPSESSID=",";"],'',$newRes[0]);
//        cache('sessionId',$sessionId);
//        $captcha = Captcha::recognize($img);
//        return $captcha;
//    }
//
//    public function getResult(){
//        $code = $this->getVerifyImg();
//        $sessionId = cache('sessionId');
//        $cookieJar = CookieJar::fromArray([
//            'PHPSESSID' => $sessionId//这边的session值，是上面获取的$a 的值，可能需要切割整理数据，你把上面的打印即可查看到值。
//        ], 'www.zhipaiwu.com');
//        $url = "http://www.zhipaiwu.com/index.php/Semblance_check/article_add.html";
//        $data = [
//            'code' => $code,
//            'content' => '<span style="color:#0000FF;font-family:Arial, Verdana, 宋体, SimSun, serif, sans-serif;font-size:18px;background-color:#FAFAFA;">是一款文章原创度在线检测工具，文章是否原创，基于文章发布光阴：相同的一篇文章，发布越早被搜索引擎收录越早，越容易被搜索引擎觉得是原创文章。文字次序：假如两个文本的信息指纹彻底相同的话，理论上能够觉得两个文本彻底相同，但是实际上却不是这样，已然搜索引擎会截取选定的字符作为信息指纹的编码，那么经过打乱这些字符的次序也能够得到不一样的信息指纹。经过以上分析原创文章和抄袭文章最大区别，在于你要发布的文章是否被搜索引擎已收录，如果你要发布的文章有大量语句被搜索引擎索引到，那么你发布的文章有可能会被搜索引擎定义为 ：抄袭文章。导致搜索引擎不收入的原因。 那么你可以通过文章照妖镜工具检测是您的文章是否属于原创文章。</span>'
//        ];
//
//        $multipart =  [
//            [
//                'name' => 'code',
//                'contents' => $code,
//            ],
//            [
//                'name' => 'content',
//                'contents' => '是一款文章原创度在线检测工具，文章是否原创，基于文章发布光阴：相同的一篇文章，发布越早被搜索引擎收录越早，越容易被搜索引擎觉得是原创文章。文字次序：假如两个文本的信息指纹彻底相同的话，理论上能够觉得两个文本彻底相同，但是实际上却不是这样，已然搜索引擎会截取选定的字符作为信息指纹的编码，那么经过打乱这些字符的次序也能够得到不一样的信息指纹。经过以上分析原创文章和抄袭文章最大区别，在于你要发布的文章是否被搜索引擎已收录，如果你要发布的文章有大量语句被搜索引擎索引到，那么你发布的文章有可能会被搜索引擎定义为 ：抄袭文章。导致搜索引擎不收入的原因。 那么你可以通过文章照妖镜工具检测是您的文章是否属于原创文章。'
//            ]
//        ];
//        $boundary = "my_custom_boundary";
//        $params = [
//            'cookies' => $cookieJar,
//            'headers' => [
//                'Content-Type' => 'multipart/form-data; boundary='.$boundary,
//                'Upgrade-Insecure-Requests' => 1,
//                "Origin" => "http://www.zhipaiwu.com",
//                "User-Agent" => "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Mobile Safari/537.36",
//                "Accept" => "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
//            ],
//            'body' => new MultipartStream($multipart,$boundary),
//            'verify' => false,
//        ];
//        $response = $this->client->request('POST',$url,$params);
//        $result = $response->getBody()->getContents();
//        dump($result);
//        $reg = "/location.href='.+.'/";
//        dump(preg_match($reg,$result,$res));
//        dump($res);
//        $href = str_replace(["'","location.href="],'',$res[0]);
//        $finalHref = str_replace('/basic/essayCheck','http://www.zhipaiwu.com',$href);
//        dump($finalHref);
//    }

    public function checkResult(){
        $url = "http://apis.5118.com/wyc/original";
        $apiKey = "097465DCAD0A4E0D9A5F34826331CF98";
        $params = [
            'headers' => [
                "Authorization" => $apiKey,
                "Content-Type" => "application/x-www-form-urlencoded; charset=UTF-8",
            ],
            "form_params" => [
                'txt' => '第一，通过此次党校学习，我加深了对党的性质的理解。党的性质是党的本质特征集中而科学的体现。党的十六大在十二大党章的基础上，重新界定了党的性质：中国共产党是中国工人阶级的先锋队，同时是中国人民和中华民族的先锋队，是中国特色社会主义事业的领导核心，代表中国先进生产力的发展要求，代表中国先进文化的前进方向，代表中国最广大人民的根本利益。党的最高理想和最终目标是实现共产主义。这段话，从党的阶级性和先进性、党的根本宗旨、党在社会主义建设事业中的地位和作用等三个方面，阐明了党的性质。此外，我也明白党的性质是党的本质特征，是无产阶级政党与其他政党的根本区别。坚持党的工人阶级先锋队性质是党的建设的根本问题，它直接关系到党的前途和命运。党的性质问题是党的建设的核心，它决定着党的组织及党的全部活动的方向。只有坚持党的工人阶级先锋队性质，并按工人阶级先锋队的要求建设党，党的建设才能有正确的方向和明确的目标，党才能始终保持先进部队的本色。否则，党的阶级基础就会发生动摇，党的指导思想、奋斗目标、宗旨、组织原则都将改变，党也会随之发生质变。第二，通过此次党校学习，我对党性的重要性有了更深的体会。党性是阶级性最高最集中的体现，是党固有的本性和特性，是党的性质在党员身上的体现，体现了党的先进性。党性的内容包括：坚持理想信念，坚定不移地为建设中国特色社会主义奋斗;坚持勤奋学习，扎扎实实提高实践“三个代表”和科学发展观;坚持党的根本宗旨，始终不渝地做到立党为公，执政为民;坚持勤奋工作，创造一流工作业绩;坚持遵守党的纪律，身体力行地维护党的团结和统一;坚持两个务必，永保中共党员的政治本色。党性锻炼的核心是提高思想认识水平，增强组织性，努力实践付诸于行动。总结起来就是增强组织性，忠诚于党与人民。在这一个月的培训过程当中，加强了我对马克思列宁主义，毛泽东思想，邓小平理论以及江泽民三个代表重要思想的学习，进一步了解到党的基本路线，党的优良传统和作风，知道了党的性质，纲领，指导思想，宗旨，组织原则和纪律，懂得党员的义务和权利。在大学,很多人把入党作为大学期间的一大任务。当然，他们的目的是不一的。有人确实是有一种进步的思想，这是无可非议的!但是，也有很多人，他们入党是为了以后毕业时能找到一份称心如意的工作，顺应潮流，毕竟，社会总是现实的，党员总是比较吃香的。我是个向往党但又有点现实思想的人，故入党也是我的大学生涯的目标与任务之一。但学习了党课之后，我的心灵受到了极大的震撼，思想也随之升华。此次党课培训,我们学习了中国共产党的性质,中国共产党的思想,党的最终目标和现阶段任务,成为共产党员的条件。我一定会不断努力,提高自身修养,以党员的条件更加严格的要求自己,提高为人民服务的本领，为祖国建设中国特色社会主义作出我作为一名预备党员应该做出的努力，继续努力成为一个优秀的、合格的、模范的共产党员。'
            ]
        ];
        $response = $this->client->request('POST',$url,$params);
        $res = json_decode($response->getBody()->getContents(),true);
        return json($res);
    }
}