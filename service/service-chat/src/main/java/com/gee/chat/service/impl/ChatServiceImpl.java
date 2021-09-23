package com.gee.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gee.servicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.chat.mapper.ChatMapper;
import com.gee.chat.entity.Chat;
import com.gee.chat.service.ChatService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Override
    public List<String> receiveUnSignMessage(Chat chat) {
        String receiveUserId = chat.getReceiveUserId();
        log.info("接收人id" + receiveUserId);

        if(StringUtils.isBlank(receiveUserId)){
            throw new GuliException(20001,"接收人id不能为空");
        }

        // 根据接收人id获取未签收的消息
        List<Chat> list = list(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getReceiveUserId, chat.getReceiveUserId())
                .eq(Chat::getSignStatus, 0));

        // 判断是否有未签收的消息
        if (list == null) {
            return Collections.EMPTY_LIST;
        }

        // 存在为签收的消息并进行转换
        ArrayList<String> chats = new ArrayList<>(list.size());
        list.forEach(item -> chats.add(item.getContent()));
        return chats;
    }
}
