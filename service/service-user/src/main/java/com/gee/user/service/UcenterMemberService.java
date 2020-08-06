package com.gee.user.service;

import com.gee.user.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gee.user.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-07-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid, String avatar, String nickname);

    int countRegister(String day);
}
