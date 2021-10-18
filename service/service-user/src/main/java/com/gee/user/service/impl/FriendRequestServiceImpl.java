package com.gee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.Friend;
import com.gee.user.entity.FriendRequest;
import com.gee.user.mapper.FriendRequestServiceMapper;
import com.gee.user.service.FriendRequestService;
import com.gee.user.service.FriendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendRequestServiceImpl extends ServiceImpl<FriendRequestServiceMapper, FriendRequest> implements FriendRequestService {
    @Resource
    private FriendService friendService;


    @Override
    public List<FriendRequest> queryAddFriendRequest(String userId) {
        return list(new LambdaQueryWrapper<FriendRequest>()
                .eq(FriendRequest::getReceiveUserId, userId));
    }

    @Override
    @Transactional
    public void handlerAddFriendRequest(FriendRequest friendRequest) {
        FriendRequest byId = getById(friendRequest.getId());
        if (byId == null) {
            throw new GuliException(20001, "该请求不存在");
        }

        // 七天前的请求
        if (byId.getIsExpire() == 1) {
            removeById(friendRequest.getId());
            throw new GuliException(20001, "该请求已过期");
        }

        if (friendRequest.getHandlerResult() == null) {
            throw new GuliException(20001, "未选择是否同意");
        } else if (friendRequest.getHandlerResult().equals(1)) {
            // 双向添加好友
            Friend friendOne = new Friend();
            friendOne.setSendUserId(friendRequest.getSendUserId());
            friendOne.setReceiveUserId(friendRequest.getReceiveUserId());
            Friend friendTwo = new Friend();
            friendTwo.setSendUserId(friendRequest.getReceiveUserId());
            friendTwo.setReceiveUserId(friendRequest.getSendUserId());
            friendService.save(friendOne);
            friendService.save(friendTwo);
        }
        // 删除好友请求
        removeById(friendRequest.getId());
    }

    @Override
    public void deleteFriend(Friend friend) {
        // 双向删除好友
        friendService.remove(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getSendUserId, friend.getSendUserId())
                .eq(Friend::getReceiveUserId, friend.getReceiveUserId()));
        friendService.remove(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getSendUserId, friend.getReceiveUserId())
                .eq(Friend::getReceiveUserId, friend.getSendUserId()));
    }
}
