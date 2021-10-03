package com.dingdong.party.serviceBase.common.api;

/**
 * 常用API返回对象接口
 * @author retraci
 */
public interface IErrorCode {
    /**
     * 返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 返回信息
     * @return 返回信息
     */
    String getMessage();
}
