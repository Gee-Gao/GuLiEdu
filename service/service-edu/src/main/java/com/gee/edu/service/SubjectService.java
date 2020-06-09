package com.gee.edu.service;

import com.gee.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-06-09
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);
}
