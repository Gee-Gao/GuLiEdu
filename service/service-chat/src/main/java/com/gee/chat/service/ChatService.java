package com.gee.chat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.chat.dto.ChatDTO;
import com.gee.chat.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ChatService extends IService<Chat>{
    List<String> receiveUnSignMessage(Chat chat);

    List<Chat> queryChatRecord(Page<Chat> pageParam, ChatDTO chat);

    void deleteHistoryChatByTime(ChatDTO chat);

    void deleteHistoryChatById(ChatDTO chatDTO);

    void sendMessage(Chat chat);

    void signMessage(List<Chat> chats);
}
