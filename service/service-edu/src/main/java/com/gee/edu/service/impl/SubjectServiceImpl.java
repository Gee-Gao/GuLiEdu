package com.gee.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.edu.entity.Subject;
import com.gee.edu.entity.excel.ExcelSubjectData;
import com.gee.edu.entity.subject.OneSubject;
import com.gee.edu.entity.subject.TwoSubject;
import com.gee.edu.listener.SubjectExcelListener;
import com.gee.edu.mapper.SubjectMapper;
import com.gee.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询出所有的一级分类
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询出所有的二级分类
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (Subject subject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);
            finalSubjectList.add(oneSubject);
            //封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (Subject subjectTwo : twoSubjectList) {
                //判断二级分类parent_id和一级分类id是否相等
                if (subjectTwo.getParentId().equals(subject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subjectTwo, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有的二级分类放到一级分类中
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
