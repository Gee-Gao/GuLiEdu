package com.gee.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.user.entity.Friend;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.service.FriendService;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl  extends ServiceImpl<FriendServiceMapper, Friend> implements FriendService {
}
