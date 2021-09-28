package com.gee.chat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.chat.dto.ChatDTO;
import com.gee.chat.entity.Chat;
import com.gee.chat.service.ChatService;
import com.gee.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {
    @Autowired
    private ChatService chatService;


    @ApiOperation("根据时间范围进行聊天记录删除")
    @DeleteMapping("deleteHistoryChatByTime")
    public R deleteHistoryChatByTime(@RequestBody ChatDTO chat) {
        chatService.deleteHistoryChatByTime(chat);
        return R.ok();
    }


    @ApiOperation("删除聊天记录")
    @DeleteMapping("deleteHistoryChatById")
    public R deleteHistoryChatById(@RequestBody ChatDTO chatDTO) {
        chatService.deleteHistoryChatById(chatDTO);
        return R.ok();
    }

    @ApiOperation("发送消息")
    @PostMapping("sendMessage")
    public R sendMessage(@RequestBody Chat chat) {
        chatService.sendMessage(chat);
        return R.ok();
    }


    @ApiOperation("获取未签收的消息")
    @PostMapping("receiveUnSignMessage")
    public R receiveMessage(@RequestBody Chat chat) {
        List<String> message = chatService.receiveUnSignMessage(chat);
        return R.ok().data("message", message);
    }


    @ApiOperation("签收消息")
    @PostMapping("signMessage")
    public R signMessage(@RequestBody List<Chat> chats){
        chatService.signMessage(chats);
        return R.ok();
    }


    @ApiOperation("查看历史记录")
    @GetMapping("queryChatRecord/{page}")
    public R queryChatRecord(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @RequestBody ChatDTO chat) {
        Page<Chat> pageParam = new Page<>(page, 10);
        List<Chat> chats = chatService.queryChatRecord(pageParam, chat);
        return R.ok().data("chats", chats);
    }
}
