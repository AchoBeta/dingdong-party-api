package com.dingdong.party.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingdong.party.admin.entity.wx.TemplateData;
import com.dingdong.party.admin.entity.wx.TemplateInfo;
import com.dingdong.party.admin.entity.wx.WxMssVo;
import com.dingdong.party.admin.service.WXService;
import com.dingdong.party.admin.utils.ConstantWxUtils;
import com.dingdong.party.admin.utils.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WXServiceImpl implements WXService {

    // 获取 access_token

    public String getAccessToken() {
        String baseAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential" +
                "&appid=%s" +
                "&secret=%s";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET
        );
        String accessToken = null;

        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            HashMap mapAccessToken = JSONObject.parseObject(accessTokenInfo, HashMap.class);
            accessToken = (String) mapAccessToken.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return accessToken;
        }
    }

    public void uniformMessage (String openId) throws Exception {
        String access_token = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="
                + access_token;
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        TemplateInfo info = new TemplateInfo();
        info.getData().put("first", new TemplateData("aaa"));
        info.getData().put("keyword1", new TemplateData("bbb"));
        info.getData().put("keyword2", new TemplateData("ccc"));
        info.getData().put("keyword3", new TemplateData("ddd"));
        wxMssVo.setMp_template_msg(info);

        String g = JSONObject.toJSONString(wxMssVo);
        System.out.println(g);

        String s = HttpClientUtils.postParameters(url, g);
        System.out.println(s);
    }
}
