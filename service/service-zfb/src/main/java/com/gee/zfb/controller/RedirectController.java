package com.gee.zfb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("callback")
    public String redirect(){
        return "redirect:http://localhost:8150/door/order.html";
    }
}
