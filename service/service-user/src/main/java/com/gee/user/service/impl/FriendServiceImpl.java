package com.gee.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.Friend;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.service.FriendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl  extends ServiceImpl<FriendServiceMapper, Friend> implements FriendService {
    @Override
    public void remarkForFriend(Friend friend) {
        if(StringUtils.isBlank(friend.getRemark())){
            throw new GuliException(20001,"备注不能为空或全为空格");
        }else {
            if (friend.getRemark().length()>20) {
                throw new GuliException(20001,"备注不能超过20个字符");
            }
        }
        updateById(friend);
    }
}
