package com.dingdong.party.serviceBase.common.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 为了契合前端的数据处理, 从而生成这个 无用的类
 *
 * @author retraci
 */
public class CommonItem<T> {

    @ApiModelProperty("单个数据")
    private T item;

    /**
     * 封装一个 T
     */
    public static <T> CommonItem<T> restItem(T item) {
        CommonItem<T> result = new CommonItem<>();
        result.setItem(item);

        return result;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
