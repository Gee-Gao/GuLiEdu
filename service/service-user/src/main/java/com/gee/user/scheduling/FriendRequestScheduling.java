package com.gee.user.scheduling;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gee.user.entity.FriendRequest;
import com.gee.user.service.FriendRequestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class FriendRequestScheduling {
    @Resource
    private FriendRequestService friendRequestService;

    // 一小时执行一次
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    // 定时删除超过7天的好友请求
    public void deleteAddFriendRequestTimer() {
        Date now = new Date();
        long time = now.getTime();
        long sevenDays = 1000 * 60 * 60 * 24 * 7L;
        Date sevenDaysAgo = new Date(time - sevenDays);
        friendRequestService.remove(new LambdaQueryWrapper<FriendRequest>()
                .between(FriendRequest::getGmtCreate, sevenDaysAgo, now));
    }

}
