package com.haiyang.controller;
import com.haiyang.common.Result;
import com.haiyang.entity.Category;
import com.haiyang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.haiyang.common.BaseController;
import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController extends BaseController {
    //需要创建CategoryService对象
    //@Autowired Spring框架自动创建好该对象
    @Autowired
    private CategoryService cService;
    //处理 前端请求所有商家分类的请求
    @GetMapping("/list")
    public Result list(){
        //获得所有的商家分类数据  sys_category表中数据
        //返回 List<Category>
        List<Category> list = cService.list();
        return Result.success(list);
    }
}
