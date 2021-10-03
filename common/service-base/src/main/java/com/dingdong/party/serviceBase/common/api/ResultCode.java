package com.dingdong.party.serviceBase.common.api;

import lombok.Getter;
import lombok.ToString;

/**
 * @author retraci
 */

@Getter
@ToString
public enum ResultCode {

    /* 成功 */
    SUCCESS(true,200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(false, 999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(false, 1001, "参数无效"),
    PARAM_IS_BLANK(false, 1002, "参数为空"),
    PARAM_TYPE_ERROR(false, 1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(false, 1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(false, 2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(false, 2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(false, 2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(false, 2004, "密码过期"),
    USER_ACCOUNT_DISABLE(false, 2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(false, 2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(false, 2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(false, 2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(false, 2009, "账号下线"),

    /* 业务错误 */
    NO_PERMISSION(false, 3001, "没有权限");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCode(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
