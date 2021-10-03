package com.dingdong.party.serviceBase.exception;

import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author retraci
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 指定出现什么异常执行这个方法
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Result<String>> error(Exception e) {
        return CommonResult.failed(ResultCode.COMMON_FAIL.getCode(), e.getMessage());
    }

    /**
     * 特定异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<Result<String>> error(RuntimeException e) {
        e.printStackTrace();
        return CommonResult.failed(ResultCode.COMMON_FAIL.getCode(), "执行了ArithmeticException异常处理..");
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(PartyException.class)
    @ResponseBody
    public ResponseEntity<Result<String>> error(PartyException e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
