package com.gee.edu.client;


import org.springframework.stereotype.Component;

/**
 * @author Gee
 */
@Component
public class OrderFileDegradeFeignClient implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
