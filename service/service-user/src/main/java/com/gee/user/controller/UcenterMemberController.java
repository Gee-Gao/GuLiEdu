package com.gee.user.controller;


import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.R;
import com.gee.commonutils.vo.UcenterMemberOrderVo;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.FriendRequest;
import com.gee.user.entity.UcenterMember;
import com.gee.user.entity.vo.RegisterVo;
import com.gee.user.service.FriendRequestService;
import com.gee.user.service.UcenterMemberService;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Gee
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/eduuser/member")
@CrossOrigin
@Slf4j
public class UcenterMemberController {
    @Resource
    private UcenterMemberService memberService;
    @Resource
    private FriendRequestService friendRequestService;


    @PostMapping("handlerAddFriendRequest")
    public R handlerAddFriendRequest(@RequestBody FriendRequest friendRequest){
        friendRequestService.handlerAddFriendRequest(friendRequest);
        return R.ok();
    }

    @GetMapping("queryAddFriendRequest/{userId}")
    public R queryAddFriendRequest(@PathVariable String userId){
       List<FriendRequest> list = friendRequestService.queryAddFriendRequest(userId);
       return R.ok().data("friendRequests",list);
    }


    @ApiOperation("发送添加好友请求")
    @PostMapping("sendAddFriendRequest")
    public R sendAddFriendRequest(@RequestBody FriendRequest friendRequest){
        friendRequestService.save(friendRequest);
        return R.ok();
    }


    @ApiOperation("修改手机号")
    @PostMapping("changeMobile")
    public R changeMobile(@RequestBody UcenterMember ucenterMember){
        memberService.changeMobile(ucenterMember);
        return R.ok();
    }

    @ApiOperation("修改密码")
    @PostMapping("changePassword")
    public R changePassword(@RequestBody UcenterMember ucenterMember){
        memberService.changePassword(ucenterMember);
        return R.ok();
    }

    @ApiOperation("查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@ApiParam("日期") @PathVariable String day) {
        int count = memberService.countRegister(day);
        log.info(day + "注册人数" + count);
        return R.ok().data("countRegister", count);
    }

    //根据id返回用户信息
    @ApiOperation("根据id返回用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrderVo getUserInfoOrder(@ApiParam("用户id") @PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象的值赋值给ucenterMemberOrderVo
        UcenterMemberOrderVo ucenterMemberOrderVo = new UcenterMemberOrderVo();
        BeanUtils.copyProperties(member, ucenterMemberOrderVo);
        log.info("用户信息" + ucenterMemberOrderVo);
        return ucenterMemberOrderVo;
    }


    //根据id查询用户
    @ApiOperation("根据id查询用户")
    @GetMapping("getUserInfoComment/{id}")
    public R getUserInfoComment(@ApiParam("用户id") @PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        log.info("用户信息" + member);
        return R.ok().data("member", member);
    }


    //登录
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember,HttpServletRequest request) {
        try {
            String token = memberService.login(ucenterMember,request);
            log.info("token" + token);
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
            log.error("注册失败");
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

    //根据openId获取UcenterMember对象
    @ApiOperation("根据OpenId获取UcenterMember对象")
    @PostMapping("getOpenIdMember")
    public R getOpenIdMember(@RequestBody UcenterMember ucenterMember) {
        //获取数据
        String openid = ucenterMember.getOpenid();
        String avatar = ucenterMember.getAvatar();
        String nickname = ucenterMember.getNickname();
        //查看数据库当前账号是否被注册
        UcenterMember member = memberService.getOpenIdMember(openid, avatar, nickname);
        //把 UcenterMember对象转换为json返回
        Gson gson = new Gson();
        String memberJson = gson.toJson(member);
        return R.ok().message(memberJson);
    }

    //微信注册
    @ApiOperation("微信注册")
    @PostMapping("registerWx")
    public R registerWx(@RequestBody UcenterMember member) {
        memberService.save(member);
        return R.ok();
    }
}

