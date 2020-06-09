package com.gee.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gee.edu.entity.Subject;
import com.gee.edu.entity.excel.ExcelSubjectData;
import com.gee.edu.service.SubjectService;
import com.gee.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        //判断一级分类是否重复,如果没有进行添加
        Subject existOneSubject = existOneSubject(subjectService, excelSubjectData.getOneSubjectName());
        if (existOneSubject == null){
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //判断二级分类是否重复,如果没有进行添加
        //获取一级分类id值
        String pid = existOneSubject.getId();
        Subject existTwoSubject = existTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null){
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
