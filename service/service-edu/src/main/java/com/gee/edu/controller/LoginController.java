package com.gee.edu.controller;

import com.gee.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin//解决跨域
public class LoginController {
    //用户登录
    @ApiOperation("用户登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    //用户信息
    @ApiOperation("用户信息")
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "admin").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
