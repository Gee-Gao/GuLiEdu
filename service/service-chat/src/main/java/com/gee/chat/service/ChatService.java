package com.gee.chat.service;

import com.gee.chat.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ChatService extends IService<Chat>{
    List<String> receiveUnSignMessage(Chat chat);
}
