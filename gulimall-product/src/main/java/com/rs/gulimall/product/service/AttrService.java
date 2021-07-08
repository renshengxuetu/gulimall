package com.rs.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.rs.gulimall.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:50
 */
public interface AttrService extends IService<AttrEntity> {

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(AttrEntity attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrEntity getAttrInfo(Long attrId);

    void updateDetail(AttrEntity attr);

    void deleteRelation(AttrAttrgroupRelationEntity[] vos);

    PageUtils getNoRelationAttr(Map<String,Object> params, Long attrgroupId);

}

