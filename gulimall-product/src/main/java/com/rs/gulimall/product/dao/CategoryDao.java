package com.rs.gulimall.product.dao;

import com.rs.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:49
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
