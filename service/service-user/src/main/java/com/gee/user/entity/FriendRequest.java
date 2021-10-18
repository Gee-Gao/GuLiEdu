package com.gee.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FriendRequest {
    @ApiModelProperty(value = "会员id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "会员id")
    private String sendUserId;

    @ApiModelProperty(value = "接收人id")
    private String receiveUserId;

    @ApiModelProperty(value = "过期状态，0未过期，1为过期")
    private Integer isExpire;

    @ApiModelProperty(value = "处理结果，0为拒绝，1为同意")
    @TableField(exist = false)
    private Integer handlerResult;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
