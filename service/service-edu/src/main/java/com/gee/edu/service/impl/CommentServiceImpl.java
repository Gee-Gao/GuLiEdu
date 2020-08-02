package com.gee.edu.service.impl;

import com.gee.edu.entity.Comment;
import com.gee.edu.mapper.CommentMapper;
import com.gee.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
