package com.gee.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.MD5;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.user.entity.UcenterMember;
import com.gee.user.entity.vo.RegisterVo;
import com.gee.user.mapper.UcenterMemberMapper;
import com.gee.user.service.UcenterMemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-07-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //登录
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (null == member) {
            throw new GuliException(20001, "手机号未注册");
        }

        //校验密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }

        //校验是否被禁用
        if (member.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    //根据openId查询用户
    public UcenterMember getOpenIdMember(String openid, String avatar, String nickname) {
        //通过openid 查询数据库是否存在相同账号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        //如果数据库中存在
        if (member != null) {
            //定义变量，判断当前登陆账号信息是否发生修改
            boolean flag = false;
            //判断头像是否修改
            if (!member.getAvatar().equals(avatar)) {
                member.setAvatar(avatar);
                flag = true;
            }
            //判断昵称是否修改
            if (!member.getNickname().equals(nickname)) {
                member.setNickname(nickname);
                flag = true;
            }
            //如果有任何一项修改过，更新数据库内容，并重新查询
            if (flag) {
                baseMapper.updateById(member);
                member = baseMapper.selectOne(wrapper);
            }
        }
        return member;
    }

    //查询某一天注册人数
    @Override
    public int countRegister(String day) {
        return baseMapper.countRegister(day);
    }

    @Override
    public void changeMobile(UcenterMember ucenterMember) {
        //校验参数
        if (StringUtils.isEmpty(ucenterMember.getOldMobile())) {
            throw new GuliException(20001, "请输入原手机号");
        }
        String mobile = ucenterMember.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001, "请输入新手机号");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!ucenterMember.getCode().equals(mobileCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<UcenterMember>()
                .eq(UcenterMember::getMobile, mobile));

        if(count>0){
            throw new GuliException(20001, "手机号已被注册");
        }

        // 更新数据库
        updateById(ucenterMember);
    }

    // 修改密码
    @Override
    public void changePassword(UcenterMember ucenterMember) {
        //校验参数
        if (StringUtils.isEmpty(ucenterMember.getMobile())) {
            throw new GuliException(20001, "账号不存在");
        }

        // 查询原密码是否正确
        UcenterMember member = getOne(new LambdaQueryWrapper<UcenterMember>()
                .eq(UcenterMember::getMobile, ucenterMember.getMobile())
                .eq(UcenterMember::getPassword, MD5.encrypt(ucenterMember.getOldPassword())));

        // 密码错误
        if (member == null) {
            throw new GuliException(20001, "原密码错误");
        }

        // 判断账号是否禁用
        if (member.getIsDisabled()) {
            throw new GuliException(20001, "账号已被禁用 ");
        }

        // 修改密码
        member.setPassword(MD5.encrypt(ucenterMember.getPassword()));
        updateById(member);
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile)  || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count > 0) {
            throw new GuliException(20001, "手机号已被注册");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }
}
