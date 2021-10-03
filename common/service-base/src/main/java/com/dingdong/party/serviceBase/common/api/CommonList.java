package com.dingdong.party.serviceBase.common.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 为了契合前端的数据处理, 从而生成这个 无用的类
 *
 * @author retraci
 */
public class CommonList<T> {

    @ApiModelProperty("列表数据")
    private CommonPage<T> list;

    /**
     * 封装一个 CommonPage
     */
    public static <T> CommonList<T> restList(List<T> list) {
        CommonPage<T> restPage = CommonPage.restPage(list);
        CommonList<T> result = new CommonList<>();
        result.setList(restPage);

        return result;
    }

    public CommonPage<T> getList() {
        return list;
    }

    public void setList(CommonPage<T> list) {
        this.list = list;
    }
}
