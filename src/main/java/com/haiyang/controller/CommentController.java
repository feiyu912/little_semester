package com.haiyang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Result;
import com.haiyang.entity.Comment;
import com.haiyang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    // 根据商家ID查询评价列表
    @GetMapping("/list/{businessId}")
    public Result list(@PathVariable Long businessId) {
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        qw.eq("business_id", businessId);
        qw.orderByDesc("created");
        List<Comment> list = commentService.list(qw);
        return Result.success(list);
    }

    // 添加评价
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        comment.setCreated(new Date());
        comment.setUpdated(new Date());
        comment.setStatu(1);
        boolean saved = commentService.save(comment);
        if (saved) {
            return Result.success("评价成功");
        } else {
            return Result.fail("评价失败");
        }
    }
} 