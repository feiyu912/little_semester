package com.haiyang.common;

import lombok.Data;

/**
 * 前端提交的更新账户信息请求
 */
@Data
public class UpdateAccountRequest {
    private String accountName;
    private Integer accountSex;
    private String accountImg;

    // 修改密码时提供这两个字段
    private String oldPassword;
    private String newPassword;
}
