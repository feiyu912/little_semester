/**
 * 软件开发中特别是在使用Spring Boot和MyBatis框架的Java应用程序中，
 * 通常指的是一个接口，它定义了如何通过SQL语句与数据库进行交互。
 * Mapper接口包含了一系列方法，每个方法都对应于一个SQL语句，
 * 这些方法可以被应用程序的其他组件调用
 */
package com.haiyang.service.impl;

import com.haiyang.entity.Category;
import com.haiyang.mapper.CategoryMapper;
import com.haiyang.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author L1n
 * @since 2025-06-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
