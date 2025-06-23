package com.haiyang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Result;
import com.haiyang.entity.Cart;
import com.haiyang.entity.Goods;
import com.haiyang.entity.Orders;
import com.haiyang.entity.Ordersdetailet;
import com.haiyang.service.BusinessService;
import com.haiyang.service.CartService;
import com.haiyang.service.GoodsService;
import com.haiyang.service.OrdersService;
import com.haiyang.service.OrdersdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;

import java.util.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController extends BaseController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersdetailetService odService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 下单 ⇒ 保存主表 + 明细，返回 orderId
     */
    @Transactional
    @PostMapping(value = "/save", consumes = "application/json")
    public Result save(@RequestBody Orders orders) {
        if (orders.getAccountId() == null
                || orders.getBusinessId() == null
                || orders.getDaId() == null
                || orders.getOrderTotal() == null) {
            return Result.fail("必填字段缺失");
        }
        // 查询购物车
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("account_id", orders.getAccountId())
                .eq("business_id", orders.getBusinessId());
        List<Cart> cartList = cartService.list(qw);
        if (cartList.isEmpty()) {
            return Result.fail("购物车为空");
        }
        // 保存订单主表
        Date now = new Date();
        orders.setCreated(now);
        orders.setUpdated(now);
        orders.setState(0);
        ordersService.save(orders);
        Long orderId = orders.getOrderId();
        // 保存明细
        List<Ordersdetailet> odList = new ArrayList<>();
        for (Cart c : cartList) {
            Ordersdetailet od = new Ordersdetailet();
            od.setOrderId(orderId);
            od.setGoodsId(c.getGoodsId());
            od.setQuantity(c.getQuantity());
            odList.add(od);
        }
        odService.saveBatch(odList);
        // 清空购物车
        cartService.remove(qw);
        return Result.success(orderId);
    }

    /**
     * 查询某用户的订单列表（带商家名和商品明细）
     */
    @GetMapping("/listByAccountId/{accountId}")
    public Result listByAccountId(@PathVariable String accountId) {
        // 1. 先查订单主表
        List<Orders> orders = ordersService.list(
                new QueryWrapper<Orders>().eq("account_id", accountId)
        );
        // 2. 组装返回实体
        List<Map<String,Object>> result = new ArrayList<>();
        for (Orders o : orders) {
            Map<String,Object> m = new HashMap<>();
            m.put("orderId", o.getOrderId());
            m.put("created", o.getCreated());
            m.put("state", o.getState());
            m.put("orderTotal", o.getOrderTotal());
            // 商家名
            m.put("businessName",
                    businessService.getById(o.getBusinessId())
                            .getBusinessName());
            // 明细列表
            List<Map<String,Object>> items = new ArrayList<>();
            List<Ordersdetailet> odList = odService.list(
                    new QueryWrapper<Ordersdetailet>()
                            .eq("order_id", o.getOrderId())
            );
            for (Ordersdetailet od : odList) {
                Goods g = goodsService.getById(od.getGoodsId());
                Map<String,Object> it = new HashMap<>();
                it.put("goodsId", od.getGoodsId());
                it.put("goodsName", g.getGoodsName());
                it.put("goodsPrice", g.getGoodsPrice());
                it.put("quantity", od.getQuantity());
                items.add(it);
            }
            m.put("items", items);
            result.add(m);
        }
        return Result.success(result);
    }

    /**
     * 标记订单为已支付
     */
    @PutMapping("/pay/{orderId}")
    public Result pay(@PathVariable Long orderId) {
        Orders o = new Orders();
        o.setOrderId(orderId);
        o.setState(1);
        o.setUpdated(new Date());
        boolean ok = ordersService.updateById(o);
        return ok ? Result.success(null) : Result.fail("支付失败");
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/delete/{orderId}")
    public Result delete(@PathVariable Long orderId) {
        boolean ok = ordersService.removeById(orderId);
        return ok ? Result.success(null) : Result.fail("删除失败");
    }

    /**
     * 查询单个订单详情（含商家名和明细）
     */
    @GetMapping("/detail/{orderId}")
    public Result detail(@PathVariable Long orderId) {
        Orders o = ordersService.getById(orderId);
        if (o == null) return Result.fail("订单不存在");
        Map<String, Object> m = new HashMap<>();
        m.put("orderId", o.getOrderId());
        m.put("created", o.getCreated());
        m.put("state", o.getState());
        m.put("orderTotal", o.getOrderTotal());
        m.put("businessName", businessService.getById(o.getBusinessId()).getBusinessName());
        // 明细列表
        List<Map<String, Object>> items = new ArrayList<>();
        List<Ordersdetailet> odList = odService.list(
                new QueryWrapper<Ordersdetailet>().eq("order_id", o.getOrderId())
        );
        for (Ordersdetailet od : odList) {
            Goods g = goodsService.getById(od.getGoodsId());
            Map<String, Object> it = new HashMap<>();
            it.put("goodsId", od.getGoodsId());
            it.put("goodsName", g.getGoodsName());
            it.put("goodsPrice", g.getGoodsPrice());
            it.put("quantity", od.getQuantity());
            items.add(it);
        }
        m.put("items", items);
        return Result.success(m);
    }
}
