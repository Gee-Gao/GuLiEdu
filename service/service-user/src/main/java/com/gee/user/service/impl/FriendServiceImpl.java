package com.gee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.Friend;
import com.gee.user.entity.UcenterMember;
import com.gee.user.entity.vo.FriendVo;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.mapper.FriendServiceMapper;
import com.gee.user.service.FriendService;
import com.gee.user.service.UcenterMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl extends ServiceImpl<FriendServiceMapper, Friend> implements FriendService {
    @Resource
    private UcenterMemberService ucenterMemberService;

    @Override
    public void remarkForFriend(Friend friend) {
        if (StringUtils.isBlank(friend.getRemark())) {
            throw new GuliException(20001, "备注不能为空或全为空格");
        } else {
            if (friend.getRemark().length() > 20) {
                throw new GuliException(20001, "备注不能超过20个字符");
            }
        }
        updateById(friend);
    }

    @Override
    public List<FriendVo> queryFriendList(String userId) {
        // 根据当前登录用户id获取自己的所有好友
        List<Friend> friends = list(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getSendUserId, userId));

        // 如果没有好友直接返回空集合
        if (friends != null && friends.size() > 0) {
            return Collections.EMPTY_LIST;
        }

        // 获取好友id
        List<String> collect = friends.stream()
                .map(item -> item.getReceiveUserId())
                .collect(Collectors.toList());

        // 根据好友id查询所有好友
        Collection<UcenterMember> members = ucenterMemberService.listByIds(collect);

        List<FriendVo> result = new ArrayList<>();

        // 利用抛异常结束forEach循环
        members.forEach(item -> {
            try {
                collect.forEach(id -> {
                    if (id.equals(item.getId())) {
                        FriendVo friendVo = new FriendVo();
                        friendVo.setAvatar(item.getAvatar());
                        friendVo.setUsername(item.getNickname());
                        friendVo.setReceiveUserId(id);
                        friendVo.setSendUserId(userId);
                        result.add(friendVo);
                        throw new GuliException(200, "break");
                    }
                });
            } catch (GuliException ignored) {
            }

        });

        return result;
    }
}
