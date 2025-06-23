package com.haiyang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Result;
import com.haiyang.entity.Deliveryaddress;
import com.haiyang.service.DeliveryaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;
import java.util.List;

@RestController
@RequestMapping("/deliveryaddress")
@CrossOrigin
public class DeliveryaddressController extends BaseController {
    @Autowired
    private DeliveryaddressService dyService;

    @GetMapping("/list/{accountId}")
    public Result list(@PathVariable String accountId){
        QueryWrapper<Deliveryaddress> qw = new QueryWrapper<>();
        qw.eq("account_id",accountId);
        List<Deliveryaddress> list = dyService.list(qw);
        if(list == null){
            return Result.fail("配送信息加载失败");
        }else{
            return Result.success(list);
        }
    }

    // 获取单个地址详情
    @GetMapping("/{daId}")
    public Result getOne(@PathVariable Integer daId) {
        Deliveryaddress address = dyService.getById(daId);
        if (address == null) {
            return Result.fail("未找到该地址");
        } else {
            return Result.success(address);
        }
    }

    // 新增收货地址
    @PostMapping("/add")
    public Result add(@RequestBody Deliveryaddress deliveryaddress) {
        if (deliveryaddress.getAccountId() == null ||
                deliveryaddress.getContactName() == null || deliveryaddress.getContactName().trim().isEmpty() ||
                deliveryaddress.getContactTel() == null || deliveryaddress.getContactTel().trim().isEmpty() ||
                deliveryaddress.getAddress() == null || deliveryaddress.getAddress().trim().isEmpty() ||
                deliveryaddress.getContactSex() == null) {
            return Result.fail("姓名、手机号、地址、性别不能为空");
        }
        boolean saved = dyService.save(deliveryaddress);
        if (saved) {
            return Result.success(20000, "新增成功", null);
        } else {
            return Result.fail("新增地址失败");
        }
    }

    // 修改收货地址
    @PostMapping("/update")
    public Result update(@RequestBody Deliveryaddress deliveryaddress) {
        if (deliveryaddress.getDaId() == null ||
                deliveryaddress.getAccountId() == null ||
                deliveryaddress.getContactName() == null || deliveryaddress.getContactName().trim().isEmpty() ||
                deliveryaddress.getContactTel() == null || deliveryaddress.getContactTel().trim().isEmpty() ||
                deliveryaddress.getAddress() == null || deliveryaddress.getAddress().trim().isEmpty() ||
                deliveryaddress.getContactSex() == null) {
            return Result.fail("ID、姓名、手机号、地址、性别不能为空");
        }
        boolean updated = dyService.updateById(deliveryaddress);
        if (updated) {
            return Result.success(20000, "更新成功", null);
        } else {
            return Result.fail("修改地址失败");
        }
    }

    // 删除收货地址
    @PostMapping("/delete/{daId}")
    public Result delete(@PathVariable Integer daId) {
        boolean removed = dyService.removeById(daId);
        if (removed) {
            return Result.success(null);
        } else {
            return Result.fail("删除地址失败");
        }
    }
}