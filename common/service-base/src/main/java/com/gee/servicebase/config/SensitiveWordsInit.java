package com.gee.servicebase.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SensitiveWordsInit {
    @Value("${sensitiveWords}")
    private List<String> sensitiveWords;
}
