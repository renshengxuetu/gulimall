package com.rs.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:42:54
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

