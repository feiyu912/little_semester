package com.haiyang.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Const;
import com.haiyang.common.Result;
import com.haiyang.common.UpdateAccountRequest;
import com.haiyang.entity.Account;
import com.haiyang.mapper.AccountMapper;
import com.haiyang.service.AccountService;
import com.haiyang.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;

import java.time.LocalDateTime;

@RestController //表明这是一个Spring MVC控制器，且所有方法返回值都自动转换为JSON响应
@RequestMapping("/account") //定义控制器的基础路径，所有方法URL都会以此路径为前缀
@Slf4j //Lombok提供的注解，自动生成日志记录器(Logger)，可以使用log.info(), log.error()等方法记录日志
@CrossOrigin //用于跨域，浏览器采用同源策略（请求协议 请求IP地址 端口号 https://xxxx:yyyy但凡有一个不一样就会被拦截）
public class AccountController extends BaseController {

    @Autowired //Spring会查找合适的bean并注入到该字段，自动注入AccountService实例
    private AccountService aService;

    //手机号验证方法
    @GetMapping("/check/{accountId}") //处理HTTP GET请求，路径为/account/check/{accountId}
    public Result check(@PathVariable String accountId){
                Account account = aService.getById(accountId);
                if(account == null){
                    return Result.success("当前手机号可被注册");
                }else{
                    return Result.fail(20005,"手机号码已被注册",null);
                }
    }

    //注册方法
    @PostMapping("/register") //处理HTTP POST请求,路径为/account/register，同样紧跟控制器路径
    //@RequestBody 将提交前端提交的json对象转为java对象。必须两边属性名完全一样。
    public Result register(@RequestBody Account account){
        //对页面提交密码在此提交
        account.setPassword(MD5Utils.md5(account.getPassword()));
        account.setCreated(LocalDateTime.now());
        account.setUpdated(LocalDateTime.now());
        account.setStatu(1); //1正常
        if(account.getAccountSex() == 1){
            account.setAccountImg(Const.DEFAULT_IMG_1);
        }else{
            account.setAccountImg(Const.DEFAULT_IMG_0);
        }
        //注册，插入数据，调用save()
        aService.save(account);
        return Result.success("用户信息注册成功");
    }

    //登录方法
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        String accountId = account.getAccountId();
        String password = account.getPassword();

        log.info("手机号为 {} 用户正在登录APP端--", accountId);

        // 查询账户
        Account dbAccount = aService.getOne(new QueryWrapper<Account>().eq("account_id", accountId));
        if (dbAccount == null) {
            return Result.fail("账户手机号码不存在");
        }

        // 密码验证
        String newPwd = MD5Utils.md5(password); // 原文密码加密
        if (newPwd.equals(dbAccount.getPassword())) {
            if (dbAccount.getStatu() == 0) {
                return Result.fail("该账户被禁用或被注销，暂不可用");
            } else {
                return Result.success(dbAccount);
            }
        } else {
            return Result.fail("登录密码不正确");
        }
    }

    // 4. 新增：获取用户信息
    @GetMapping("/{accountId}")
    public Result getAccountById(@PathVariable String accountId) {
        Account account = aService.getById(accountId);
        if (account != null) {
            return Result.success(account);
        } else {
            return Result.fail("账户不存在");
        }
    }

    // 5.用于更新用户资料（昵称、性别、头像等）

    @PutMapping("/{accountId}")
    public Result updateAccount(
            @PathVariable String accountId,
            @RequestBody UpdateAccountRequest req
    ) {
        Account account = aService.getById(accountId);
        if (account == null) {
            return Result.fail("账户不存在");
        }

        // 1) 如果前端传了 oldPassword + newPassword，则做密码校验与更新
        if (req.getOldPassword() != null && req.getNewPassword() != null) {
            String md5Old = MD5Utils.md5(req.getOldPassword());
            if (!md5Old.equals(account.getPassword())) {
                return Result.fail("旧密码不正确");
            }
            account.setPassword(MD5Utils.md5(req.getNewPassword()));
        }

        // 2) 更新昵称、性别、头像
        account.setAccountName(req.getAccountName());
        account.setAccountSex(req.getAccountSex());
        account.setAccountImg(req.getAccountImg());
        account.setUpdated(LocalDateTime.now());

        boolean ok = aService.updateById(account);
        if (ok) {
            return Result.success("账户信息更新成功");
        } else {
            return Result.fail("账户信息更新失败");
        }
    }

    /** 注销（删除）账户 */
    @DeleteMapping("/{accountId}")
    public Result deleteAccount(@PathVariable String accountId) {
        boolean removed = aService.removeById(accountId);
        if (removed) {
            return Result.success("账户已注销，感谢您的一路陪伴！");
        }
        return Result.fail("注销失败");
    }

}