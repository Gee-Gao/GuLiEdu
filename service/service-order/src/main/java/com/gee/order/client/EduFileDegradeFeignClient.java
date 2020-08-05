package com.gee.order.client;

import com.gee.commonutils.vo.UcenterMemberOrderVo;
import org.springframework.stereotype.Component;

/**
 * @author Gee
 */
@Component
public class EduFileDegradeFeignClient implements UserClient{
    @Override
    public UcenterMemberOrderVo getUserInfoOrder(String id) {
        return null;
    }
}
