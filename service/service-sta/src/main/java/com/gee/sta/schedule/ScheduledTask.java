package com.gee.sta.schedule;

import com.gee.sta.service.StatisticsDailyService;
import com.gee.sta.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Gee
 */
@Component
public class ScheduledTask {
    @Resource
    private StatisticsDailyService dailyService;

    //每天凌晨1点执行定时
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.countRegister(day);
    }
}
