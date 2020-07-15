package com.gee.edu.entity.vo;

import lombok.Data;

/**
 * @author Gee
 */
@Data
public class CoursePublishVo {
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
