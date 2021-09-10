package com.dingdong.party.serviceBase.exception;

import com.dingdong.party.commonUtils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e) {
        return Result.error().message(e.getMessage());
    }

    //特定异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody //为了返回数据
    public Result error(RuntimeException e) {
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常
    @ExceptionHandler(PartyException.class)
    @ResponseBody //为了返回数据
    public Result error(PartyException e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return Result.error().code(e.getCode()).message(e.getMessage());
    }

}
