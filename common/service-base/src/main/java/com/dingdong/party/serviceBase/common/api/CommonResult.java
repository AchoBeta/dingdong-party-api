package com.dingdong.party.serviceBase.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 通用返回对象
 * @author retraci
 */
public class CommonResult<T> {
    /**
     * 获取状态码
     */
    public static HttpStatus getHttpStatus(int code) {
        for (HttpStatus httpStatus : HttpStatus.values()) {
            if (httpStatus.value() == code) {
                return httpStatus;
            }
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * 获取 ResponseEntity
     */
    public static <T> ResponseEntity<T> getResponseEntity(T result, HttpStatus httpStatus) {
        return new ResponseEntity<T>(result, httpStatus);
    }

    /**
     * 成功返回结果 200
     */
    public static <T> ResponseEntity<Result<T>> success(T data) {
        int code = ResultCode.SUCCESS.getCode();
        Result<T> result = new Result<>(code, null, null, data);
        return CommonResult.getResponseEntity(result, CommonResult.getHttpStatus(code));
    }

    /**
     * 成功, 无返回 200
     */
    public static <T> ResponseEntity<Result<T>> noContent(String message) {
        int code = ResultCode.SUCCESS.getCode();
        Result<T> result = new Result<>(code, message, null, null);
        return CommonResult.getResponseEntity(result, CommonResult.getHttpStatus(code));
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseEntity<Result<T>> failed(int code, String message) {
        Result<T> result = new Result<T>(code, message, null, null);
        return CommonResult.getResponseEntity(result, CommonResult.getHttpStatus(code));
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseEntity<Result<T>> failed(int code, String message, String path) {
        Result<T> result = new Result<>(code, message, path, null);
        return CommonResult.getResponseEntity(result, CommonResult.getHttpStatus(code));
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseEntity<Result<T>> failed(int code, String message, String path, T data) {
        Result<T> result = new Result<T>(code, message, path, data);
        return CommonResult.getResponseEntity(result, CommonResult.getHttpStatus(code));
    }

}
