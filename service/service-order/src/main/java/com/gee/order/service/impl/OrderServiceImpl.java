package com.gee.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.commonutils.vo.CourseOrderVo;
import com.gee.commonutils.vo.UcenterMemberOrderVo;
import com.gee.order.client.EduClient;
import com.gee.order.client.UserClient;
import com.gee.order.entity.Order;
import com.gee.order.mapper.OrderMapper;
import com.gee.order.service.OrderService;
import com.gee.order.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;
    @Resource
    private UserClient userClient;

    //创建订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //通过远程调用获取课程信息
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //通过远程调用获取用户信息
        UcenterMemberOrderVo userInfoOrder = userClient.getUserInfoOrder(memberId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
