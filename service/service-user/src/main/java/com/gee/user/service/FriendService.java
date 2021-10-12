package com.gee.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gee.user.entity.Friend;
import com.gee.user.entity.vo.FriendVo;

import java.util.List;

public interface FriendService extends IService<Friend> {

    void remarkForFriend(Friend friend);

    List<FriendVo> queryFriendList(String userId);
}
