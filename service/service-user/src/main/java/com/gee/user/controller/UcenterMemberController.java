package com.gee.user.controller;


import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.R;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.UcenterMember;
import com.gee.user.entity.vo.RegisterVo;
import com.gee.user.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Gee
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/eduuser/member")
@CrossOrigin
public class UcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    //登录
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        try {
            String token = memberService.login(ucenterMember);
            return R.ok().data("token", token);
        } catch (GuliException e) {
            return R.error().message(e.getMsg());
        }
    }

    //注册
    @ApiOperation("注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        try {
            memberService.register(registerVo);
            return R.ok();
        } catch (GuliException e) {
            return R.error().message(e.getMsg());
        }
    }

    //根据token获取登录信息
    @ApiOperation("根据token获取登录信息")
    @GetMapping("/auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("item", member);
    }
}

