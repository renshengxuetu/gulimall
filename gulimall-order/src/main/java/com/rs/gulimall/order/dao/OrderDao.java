package com.rs.gulimall.order.dao;

import com.rs.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:46:19
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
