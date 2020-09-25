package com.rs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:49
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

