package com.gee.msg.controller;

import com.gee.commonutils.R;
import com.gee.msg.service.MsgService;
import com.gee.msg.utils.RandomUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
            return R.ok();
        }

        //生成随机数，传递到阿里云进行发送
        code = RandomUtils.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        boolean isSend = msgService.send(param, phone);
        if (isSend) {
            //发送成功，把验证码保存到redis，并设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }

    }
}
