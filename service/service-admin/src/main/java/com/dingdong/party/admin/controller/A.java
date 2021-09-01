package com.dingdong.party.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录逻辑")
@RequestMapping("")
@RestController
public class A {

    @GetMapping("a")
    public String b() {
        return "b";
    }

    @PostMapping("a")
    public String d() {
        return "d";
    }

    @DeleteMapping("a")
    public String a() {
        return "a";
    }

    @PutMapping("a")
    public String c() {
        return "c";
    }
}
