package com.rs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:49
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

