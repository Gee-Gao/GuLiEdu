package com.gee.edu.entity.subject;

import lombok.Data;

//二级分类
@Data
public class TwoSubject {
    private String id;
    private String title;
    private String parentId;
}
