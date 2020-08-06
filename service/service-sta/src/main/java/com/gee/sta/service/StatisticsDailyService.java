package com.gee.sta.service;

import com.gee.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-08-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegister(String day);
}
