package com.dingdong.party.serviceBase.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dingdong.party.commonUtils.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: dingdong-party-api
 * @author: retraci
 * @create: 2021-09-01 12:53
 **/
//@Order(0)
//@Aspect
//@Component
public class ResultCamel2UnderlineAspect {

    @Pointcut("execution(public com.dingdong.party.commonUtils.result.Result *.*(..))")
    public void ResultCamel2Underline() {
    }

    @Around("ResultCamel2Underline()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Map<String, Object> res = dfs(jsonObject);

        return BeanUtil.toBean(res, Result.class);
    }

    private Map<String, Object> dfs(Map<String, Object> root) {
        if (root == null) return null;

        return root.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        e -> {
                            // key 的处理
                            return StrUtil.toUnderlineCase(e.getKey());
                        },
                        e -> {
                            // value 的处理
                            Object val = e.getValue();
                            Object newVal = val;

                            // Map => 递归
                            if (val instanceof Map) {
                                newVal = dfs((Map<String, Object>) val);
                            }
                            // List => 遍历, 递归
                            if (val instanceof List) {
                                newVal = ((List<?>) val).parallelStream()
                                        .map(x -> {
                                            if (x instanceof Map) {
                                                return dfs((Map<String, Object>) x);
                                            }
                                            return x;
                                        }).collect(Collectors.toList());
                            }

                            return newVal;
                        }
                ));
    }
}
