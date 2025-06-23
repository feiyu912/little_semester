package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_deliveryaddress")
public class Deliveryaddress implements Serializable {
    @TableId(value = "da_id", type = IdType.AUTO)
    private Integer daId;

    @TableField("contact_name")
    private String contactName;

    @TableField("contact_sex")
    private Integer contactSex;

    @TableField("contact_tel")
    private String contactTel;

    @TableField("address")
    private String address;

    @TableField("account_id")
    private String accountId;

    @TableField("created")
    private Date created;

    @TableField("updated")
    private Date updated;

    @TableField("statu")
    private Integer statu;
}
