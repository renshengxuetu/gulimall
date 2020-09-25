package com.rs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:49
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

