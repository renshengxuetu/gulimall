package com.rs.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:46:19
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

