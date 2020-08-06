package com.gee.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gee.user.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Gee
 * @since 2020-07-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询某一天注册人数
    int countRegister(String day);
}
