package com.gee.chat.dto;

import com.gee.chat.entity.Chat;
import lombok.Data;

import java.util.Date;

@Data
public class ChatDTO extends Chat {
    private Date startTime;
    private Date endTime;
    private String userId;
}
