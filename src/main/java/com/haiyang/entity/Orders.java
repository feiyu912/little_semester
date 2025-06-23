package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sys_orders")
public class Orders implements Serializable {
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @TableField("account_id")
    private String accountId;

    @TableField("business_id")
    private Long businessId;

    @TableField("created")
    private Date created;

    @TableField("order_total")
    private BigDecimal orderTotal;

    @TableField("da_id")
    private Integer daId;

    @TableField("state")
    private Integer state;

    @TableField("updated")
    private Date updated;
} 