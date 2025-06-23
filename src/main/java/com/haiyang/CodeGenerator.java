package com.haiyang;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {
    //需要生成哪张表的一套代码，从控制台输入表名。该代码接收输入表名。
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/system?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", 
                "root", "123456")
            // 全局配置
            .globalConfig(builder -> {
                builder.author("L1n") // 设置作者
                    .enableSwagger() // 开启 swagger 模式
                    .disableOpenDir() // 禁止打开输出目录
                    .outputDir(projectPath + "/src/main/java"); // 指定输出目录
            })
            // 包配置
            .packageConfig(builder -> {
                builder.parent("com.haiyang") // 设置父包名
                    .entity("entity") // 设置实体包名
                    .service("service") // 设置service包名
                    .serviceImpl("service.impl") // 设置serviceImpl包名
                    .mapper("mapper") // 设置mapper包名
                    .controller("controller") // 设置controller包名
                    .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
            })
            // 策略配置
            .strategyConfig(builder -> {
                builder.addInclude(scanner("表名，多个英文逗号分割").split(",")) // 设置需要生成的表名
                    // Entity策略配置
                    .entityBuilder()
                    .enableLombok() // 开启lombok
                    .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                    .naming(NamingStrategy.underline_to_camel) // 表名转换方式
                    .columnNaming(NamingStrategy.underline_to_camel) // 列名转换方式
                    .superClass("com.haiyang.common.BaseEntity") // 设置父类
                    .addSuperEntityColumns("created", "updated", "statu") // 添加父类公共字段
                    .enableRemoveIsPrefix() // 开启 Boolean 类型字段移除 is 前缀
                    .enableActiveRecord() // 开启 activeRecord 模式
                    .build()
                    // Controller策略配置
                    .controllerBuilder()
                    .enableRestStyle() // 开启生成@RestController 控制器
                    .superClass("com.haiyang.common.BaseController") // 设置父类
                    .enableHyphenStyle() // 开启驼峰转连字符
                    .build()
                    // Service策略配置
                    .serviceBuilder()
                    .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                    .formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称
                    .build()
                    // Mapper策略配置
                    .mapperBuilder()
                    .formatMapperFileName("%sMapper") // 格式化 mapper 文件名称
                    .formatXmlFileName("%sMapper") // 格式化 xml 文件名称
                    .build();
            })
            // 模板引擎配置
            .templateEngine(new FreemarkerTemplateEngine())
            // 执行
            .execute();
    }
}