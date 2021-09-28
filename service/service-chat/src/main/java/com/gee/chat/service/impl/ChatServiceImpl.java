package com.gee.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.chat.dto.ChatDTO;
import com.gee.servicebase.config.SensitiveWordsInit;
import com.gee.servicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.chat.mapper.ChatMapper;
import com.gee.chat.entity.Chat;
import com.gee.chat.service.ChatService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Resource
    private SensitiveWordsInit sensitiveWordsInit;

    @Override
    public void sendMessage(Chat chat) {
        List<String> sensitiveWords = sensitiveWordsInit.getSensitiveWords();
        String content = chat.getContent();
        // 替换敏感词汇
        for (String sensitiveWord : sensitiveWords) {
            // 判断评论中是否包含当前敏感词
            if (content.contains(sensitiveWord)) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < sensitiveWord.length(); i++) {
                    stringBuilder.append("*");
                }
                content = content.replaceAll(sensitiveWord, stringBuilder.toString());
            }
        }
        // 设置没有敏感词的评论
        chat.setContent(content);
        // 设置签收状态为未签收
        chat.setSignStatus(0);
        save(chat);
    }

    @Override
    public void deleteHistoryChatById(ChatDTO chat) {
        // 数据校验
        if (StringUtils.isBlank(chat.getUserId())) {
            throw new GuliException(20001, "当前登录用户id不能为空");
        }
        if (StringUtils.isBlank(chat.getId())) {
            throw new GuliException(20001, "id不能为空");
        }

        // 根据id查找聊天记录
        Chat byId = getById(chat.getId());
        if (byId == null) {
            return;
        }

        // 设置删除人id
        String deleteUserId = byId.getHistoryDeleteUserId();
        if (StringUtils.isBlank(deleteUserId)) {
            byId.setHistoryDeleteUserId("|" + chat.getUserId() + "|");
        } else {
            byId.setHistoryDeleteUserId(deleteUserId + "|" + chat.getUserId() + "|");
        }
        // 修改数据库
        updateById(byId);
    }

    @Override
    public void deleteHistoryChatByTime(ChatDTO chat) {
        // 数据校验
        if (StringUtils.isBlank(chat.getUserId())) {
            throw new GuliException(20001, "当前登录用户id不能为空");
        }
        if (chat.getStartTime() == null) {
            throw new GuliException(20001, "开始时间不能为空");
        }
        if (chat.getEndTime() == null) {
            throw new GuliException(20001, "结束时间不能为空");
        }

        // 获取发送人接收人id
        String sendUserId = chat.getSendUserId();
        String receiveUserId = chat.getReceiveUserId();

        List<Chat> list = list(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getReceiveUserId, receiveUserId)
                .eq(Chat::getSendUserId, sendUserId)
                .between(Chat::getGmtCreate, chat.getStartTime(), chat.getEndTime()));

        // 没有需要删除的聊天记录直接返回
        if (list.size() == 0) {
            return;
        }

        // 设置删除人id
        list.forEach(item -> {
            String deleteUserId = item.getHistoryDeleteUserId();
            if (StringUtils.isBlank(deleteUserId)) {
                item.setHistoryDeleteUserId("|" + chat.getUserId() + "|");
            } else {
                item.setHistoryDeleteUserId(deleteUserId + "|" + chat.getUserId() + "|");
            }
        });

        updateBatchById(list);
    }

    @Override
    public List<Chat> queryChatRecord(Page<Chat> pageParam, ChatDTO chat) {
        String receiveUserId = chat.getReceiveUserId();
        String sendUserId = chat.getSendUserId();

        // 数据校验
        if (StringUtils.isBlank(sendUserId)) {
            throw new GuliException(20001, "发送人id不能为空");
        }
        if (StringUtils.isBlank(receiveUserId)) {
            throw new GuliException(20001, "接收人id不能为空");
        }

        // 分页查询
        IPage<Chat> page = page(pageParam, new LambdaQueryWrapper<Chat>()
                .eq(Chat::getSendUserId, sendUserId)
                .eq(Chat::getReceiveUserId, receiveUserId)
                .eq(Chat::getIsDeleted, 0)
                .notLike(Chat::getHistoryDeleteUserId, "|" + chat.getUserId() + "|")
                .orderByDesc(Chat::getGmtCreate));
        return page.getRecords();
    }

    @Override
    public List<String> receiveUnSignMessage(Chat chat) {
        String receiveUserId = chat.getReceiveUserId();
        log.info("接收人id" + receiveUserId);

        if (StringUtils.isBlank(receiveUserId)) {
            throw new GuliException(20001, "接收人id不能为空");
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
