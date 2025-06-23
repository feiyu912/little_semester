package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("sys_ordersdetailet")
public class Ordersdetailet implements Serializable {
    @TableId(value = "od_id", type = IdType.AUTO)
    private Long odId;

    @TableField("order_id")
    private Long orderId;

    @TableField("goods_id")
    private Integer goodsId;

    @TableField("quantity")
    private Integer quantity;
} 