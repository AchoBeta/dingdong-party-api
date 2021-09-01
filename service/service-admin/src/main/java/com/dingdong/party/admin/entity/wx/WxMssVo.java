package com.dingdong.party.admin.entity.wx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序推送所需要的数据
 */
@Data
@Accessors(chain = true)
public class WxMssVo {

    private String touser;   // 用户openid
    private TemplateInfo mp_template_msg;
}
