package com.gee.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.commonutils.R;
import com.gee.sta.client.UserClient;
import com.gee.sta.entity.StatisticsDaily;
import com.gee.sta.mapper.StatisticsDailyMapper;
import com.gee.sta.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-08-05
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Resource
    private UserClient userClient;

    //统计某一天的注册人数
    @Override
    public void countRegister(String day) {
        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);
        //获取统计信息
        R r = userClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");
        //向数据库中添加数据
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister);
        daily.setDateCalculated(day);
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        daily.setLoginNum(RandomUtils.nextInt(100, 200));
        daily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(daily);
    }
}
