package com.dingdong.party.admin.entity.wx;

import com.dingdong.party.admin.utils.ConstantWxUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * 设置推送的文字和颜色
 */
@Data
@Accessors(chain = true)
public class TemplateInfo {
    private String appid = ConstantWxUtils.WX_OPEN_OPEN_ID;   // 公众号appid
    private String template_id = "WfPxh3rEhjhP43B2_Q5XwTlHygWmx0kFFQZ57_a7XEQ";
    private String url = "/page/index";
    private HashMap<String, String> miniprogram;
    private HashMap<String, TemplateData> data;

    public TemplateInfo() {
        miniprogram = new HashMap<>();
        miniprogram.put("appid", ConstantWxUtils.WX_OPEN_APP_ID);
        miniprogram.put("pagepath", "index");
        data = new HashMap<>();
    }
}
