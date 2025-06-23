package com.haiyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyang.entity.Comment;
import com.haiyang.mapper.CommentMapper;
import com.haiyang.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
} 