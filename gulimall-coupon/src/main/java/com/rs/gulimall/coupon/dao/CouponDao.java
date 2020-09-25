package com.rs.gulimall.coupon.dao;

import com.rs.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:42:55
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
