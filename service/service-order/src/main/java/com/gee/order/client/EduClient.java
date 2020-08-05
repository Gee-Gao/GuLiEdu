package com.gee.order.client;

import com.gee.commonutils.vo.CourseOrderVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Gee
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据id查询课程信息
    @PostMapping("/eduservice/course/getCourseInfoOrder/{id}")
    CourseOrderVo getCourseInfoOrder(@ApiParam("课程id") @PathVariable("id") String id);
}
