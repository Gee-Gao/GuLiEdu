package com.gee.chat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.chat.entity.Chat;
import com.gee.chat.service.ChatService;
import com.gee.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {
    @Resource
    private ChatService chatService;

    @ApiOperation("发送消息")
    @PostMapping("sendMessage")
    public R sendMessage(@RequestBody Chat chat) {
        log.info("消息" + chat);
        chat.setSignStatus(0);
        chatService.save(chat);
        return R.ok();
    }


    @ApiOperation("获取未签收的消息")
    @PostMapping("receiveUnSignMessage")
    public R receiveMessage(@RequestBody Chat chat) {
        List<String> message = chatService.receiveUnSignMessage(chat);
        return R.ok().data("message", message);
    }

    @ApiOperation("查看历史记录")
    @GetMapping("queryChatRecord/{page}")
    public R queryChatRecord(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @RequestBody Chat chat) {
        Page<Chat> pageParam = new Page<>(page, 10);
        List<Chat> chats = chatService.queryChatRecord(pageParam, chat);
        return R.ok().data("chats", chats);
    }
}
