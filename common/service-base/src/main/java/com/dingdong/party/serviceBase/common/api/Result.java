package com.dingdong.party.serviceBase.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author retraci
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Result<T> {

    @ApiModelProperty(value = "返回码", position = 1)
    private int code;
    @ApiModelProperty(value = "提示信息", position = 2)
    private String message;
    @ApiModelProperty(value = "路径", position = 3)
    private String path;
    @ApiModelProperty(value = "数据", position = 4)
    private T data;

    public Result() {}

    public Result(int code, String message, String path, T data) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.data = data;
    }

}
