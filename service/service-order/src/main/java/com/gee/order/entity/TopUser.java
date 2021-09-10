package com.gee.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUser {
    @ApiModelProperty("支付金额（分）")
    private BigDecimal totalFee;

    @ApiModelProperty("支付人")
    private String nickname;

    @ApiModelProperty("支付人头像")
    private String avatar;
}
