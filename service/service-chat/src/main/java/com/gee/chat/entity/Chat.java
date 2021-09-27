package com.gee.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "com-gee-chat-entity-Chat")
@Data
@TableName(value = "chat")
public class Chat {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 发送人id
     */
    @TableField(value = "send_user_id")
    @ApiModelProperty(value = "发送人id")
    private String sendUserId;

    /**
     * 接收人id
     */
    @TableField(value = "receive_user_id")
    @ApiModelProperty(value = "接收人id")
    private String receiveUserId;

    /**
     * 签收状态0未签收1已签收
     */
    @TableField(value = "sign_status")
    @ApiModelProperty(value = "签收状态0未签收1已签收")
    private Integer signStatus;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "消息内容")
    private String content;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    /**
     * 聊天记录删除人
     */
    @ApiModelProperty(value = "聊天记录删除人")
    private String historyDeleteUserId;
}