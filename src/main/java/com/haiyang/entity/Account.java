package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.haiyang.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
//这个 Account 类是一个 MyBatis-Plus 实体类
/**
 * <p>
 * 
 * </p>
 *
 * @author L1n
 * @since 2025-06-20
 */
@Data
/**
 * Lombok 注解，自动生成所有字段的 getter 和 setter 方法 toString() 方法
 * equals() 和 hashCode() 方法和无参构造函数
 */
@EqualsAndHashCode(callSuper = true)
/**
 * Lombok 注解，生成 equals() 和 hashCode() 方法时会包含父类的字段
 * callSuper = true 表示会调用父类的相应方法
 */
@TableName("sys_account")//指定该类映射的数据库表名,此处表示该类对应数据库中的 sys_account 表
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.INPUT)//主键手动录入，策略。还有UUID，随机一段字符作为主键。
    private String accountId;

    @TableField("password")
    private String password;

    @TableField("account_name")
    private String accountName;

    @TableField("account_sex")
    private Integer accountSex;

    @TableField("account_img")
    private String accountImg;

    @TableField("del_tag")
    private Integer delTag;


}
