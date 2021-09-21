package com.gee.msg.controller;

import com.gee.commonutils.R;
import com.gee.msg.service.MsgService;
import com.gee.msg.utils.RandomUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Gee
 */
@RestController
@CrossOrigin
@RequestMapping("edumsg/msg")
@Slf4j
public class MsgController {
    @Resource
    private MsgService msgService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //发送短信
    @ApiOperation("发送短信")
    @GetMapping("send/{phone}")
    public R sendMsg(@ApiParam("手机号") @PathVariable("phone") String phone) {
        //从redis中获取验证码，如果有直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            log.info("redis中存在验证码，直接返回");
            return R.ok();
        }

        //生成随机数，传递到阿里云进行发送
        code = RandomUtils.getSixBitRandom();
        log.info("生成的验证码" + code);

        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        if (msgService.send(param, phone)) {
            //发送成功，把验证码保存到redis，并设置有效时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            log.info("redis保存成功,验证码为"+code);
            return R.ok();
        } else {
            log.error("短信发送失败");
            return R.error().message("短信发送失败");
        }
    }
}
