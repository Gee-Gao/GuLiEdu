package com.gee.sta.controller;


import com.gee.commonutils.R;
import com.gee.sta.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/edusta/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Resource
    private StatisticsDailyService statisticsDailyService;

    //统计某一天的注册人数
    @ApiOperation("查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@ApiParam("日期") @PathVariable String day) {
        statisticsDailyService.countRegister(day);
        return R.ok();
    }

}

