package com.gee.msg.service;

import java.util.Map;

/**
 * @author Gee
 */
public interface MsgService {
    boolean send(Map<String, Object> param, String phone);
}
