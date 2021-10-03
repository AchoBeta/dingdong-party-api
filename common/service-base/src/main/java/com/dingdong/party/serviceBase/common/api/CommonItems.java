package com.dingdong.party.serviceBase.common.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 为了契合前端的数据处理, 从而生成这个 无用的类
 *
 * @program: api
 * @author: retraci
 * @create: 2021-10-03 21:36
 **/
public class CommonItems<T> {

    @ApiModelProperty("多组数据")
    private List<T> items;

    /**
     * 封装一个 T
     */
    public static <T> CommonItems<T> restItems(List<T> items) {
        CommonItems<T> result = new CommonItems<>();
        result.setItems(items);

        return result;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

