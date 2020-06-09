package com.gee.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.edu.entity.Subject;
import com.gee.edu.entity.excel.ExcelSubjectData;
import com.gee.edu.listener.SubjectExcelListener;
import com.gee.edu.mapper.SubjectMapper;
import com.gee.edu.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-06-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
