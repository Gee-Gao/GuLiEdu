package com.gee.servicebase.exceptionhandler;

import com.gee.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //出现任何异常都会执行
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        e.printStackTrace();
        return R.error().message("执行了空指针异常处理");
    }

    //特定异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message(e.getCode() + e.getMsg());
    }
}