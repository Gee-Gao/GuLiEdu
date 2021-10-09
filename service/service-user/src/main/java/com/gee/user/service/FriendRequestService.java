package com.gee.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gee.user.entity.FriendRequest;

import java.util.List;

public interface FriendRequestService extends IService<FriendRequest> {
    List<FriendRequest> queryAddFriendRequest(String userId);

    void handlerAddFriendRequest(FriendRequest friendRequest);
}
