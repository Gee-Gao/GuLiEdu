package com.gee.user.entity.vo;

import com.gee.user.entity.Friend;
import lombok.Data;

@Data
public class FriendVo extends Friend {
    private String avatar;

    private String username;
}
