package com.dingdong.party.admin.entity.wx;

import lombok.Data;

@Data
public class TemplateData {

    private String value;

    public TemplateData(String value) {
        this.value = value;
    }
}
