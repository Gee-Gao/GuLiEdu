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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //图表显示
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        //查询数据
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> dayList = baseMapper.selectList(dayQueryWrapper);

        //创建日期集合和数据集合
        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();

        //遍历集合，进行日期和数据封装
        for (StatisticsDaily daily : dayList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把数据封装到map并返回
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }

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
