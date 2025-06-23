package com.haiyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyang.entity.Orders;
import com.haiyang.mapper.OrdersMapper;
import com.haiyang.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
} 