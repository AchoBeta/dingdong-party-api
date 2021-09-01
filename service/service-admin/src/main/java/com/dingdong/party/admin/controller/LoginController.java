package com.dingdong.party.admin.controller;

import com.dingdong.party.admin.service.WXService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(tags = "登录逻辑")
@RequestMapping("")
@RestController
public class LoginController {

    // 验证码
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private WXService wxService;

    @ApiOperation("获取图像验证码")
    @GetMapping("/image-code")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("aaa");
        // 设置页面缓存方式，不缓存，不存储
        response.setHeader("Cache-Control", "no-store, no-cache");
        // 设置以图片的形式响应
        response.setContentType("image/jpeg");
        // 1. 获取验证码字符串
        String code = defaultKaptcha.createText();
        // 2. 字符串把他放到 session 中
        request.getSession().setAttribute("IMAGE_CODE", code);
        // 3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        // 4. 将验证码图片写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "ipg", out);
        if (out != null)
            out.close();
    }
}