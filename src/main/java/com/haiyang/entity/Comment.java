package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_comment")
public class Comment implements Serializable {
    @TableId(value = "co_id", type = IdType.AUTO)
    private Integer coId;

    @TableField("account_id")
    private String accountId;

    @TableField("business_id")
    private Long businessId;

    @TableField("order_id")
    private Long orderId;

    @TableField("rate")
    private Double rate;

    @TableField("co_text")
    private String coText;

    @TableField("co_img")
    private String coImg;

    @TableField("created")
    private Date created;

    @TableField("updated")
    private Date updated;

    @TableField("statu")
    private Integer statu;
} 