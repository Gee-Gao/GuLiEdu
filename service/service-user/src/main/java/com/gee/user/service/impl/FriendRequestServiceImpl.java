package com.gee.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.user.entity.FriendRequest;
import com.gee.user.mapper.FriendRequestServiceMapper;
import com.gee.user.service.FriendRequestService;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl extends ServiceImpl<FriendRequestServiceMapper, FriendRequest> implements FriendRequestService {
}
