package com.rs.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rs.common.constant.ProductConstant;
import com.rs.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.rs.gulimall.product.dao.AttrGroupDao;
import com.rs.gulimall.product.dao.CategoryDao;
import com.rs.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.rs.gulimall.product.entity.AttrGroupEntity;
import com.rs.gulimall.product.entity.CategoryEntity;
import com.rs.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.common.utils.PageUtils;
import com.rs.common.utils.Query;

import com.rs.gulimall.product.dao.AttrDao;
import com.rs.gulimall.product.entity.AttrEntity;
import com.rs.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        // 查询属性分组关联表信息
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> attrIdList = entities.stream().map((entity) -> {
            return entity.getAttrId();
        }).collect(Collectors.toList());
        if(attrIdList != null && attrIdList.size() > 0){
            Collection<AttrEntity> attrList = this.listByIds(attrIdList);
            return (List<AttrEntity>)attrList;
        }else{
            return new ArrayList<AttrEntity>();
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveDetail(AttrEntity attr) {
        this.save(attr);
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode() );
        if(catelogId != 0){
            queryWrapper.eq("catelog_id",catelogId);
        }
        String key = (String)params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.and((wq)->{
               wq.eq("attr_id", key).or().eq("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrEntity> datas = records.stream().map((item) -> {
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity agre = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", item.getAttrId()));
                if (agre != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(agre.getAttrGroupId());
                    item.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(item.getCatelogId());
            if (categoryEntity != null) {
                item.setCatelogName(categoryEntity.getName());
            }
            return item;
        }).collect(Collectors.toList());
        pageUtils.setList(datas);
        return pageUtils;
    }

    @Override
    public AttrEntity getAttrInfo(Long attrId) {
        AttrEntity ae = this.getById(attrId);
        if(ae.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            // 设置分组信息
            AttrAttrgroupRelationEntity agre = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", ae.getAttrId()));
            if (agre != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(agre.getAttrGroupId());
                ae.setGroupName(attrGroupEntity.getAttrGroupName());
                ae.setAttrGroupId(attrGroupEntity.getAttrGroupId());
            }
        }
        // 设置分类信息
        Long[] catelogPath = categoryService.findCatelogPath(ae.getCatelogId());
        if(catelogPath != null){
            ae.setCatelogPath(catelogPath);
        }
        CategoryEntity categoryEntity = categoryDao.selectById(ae.getCatelogId());
        if(categoryEntity != null){
            ae.setCatelogName(categoryEntity.getName());
        }
        return ae;
    }

    @Transactional
    @Override
    public void updateDetail(AttrEntity attr) {
        this.updateById(attr);

        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            // 修改分组
            if(attr.getAttrGroupId() != null){
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
                attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
                Integer attr_id = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
                if(attr_id > 0){
                    attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
                }else{
                    attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
                }
            }
        }
    }

    @Override
    public void deleteRelation(AttrAttrgroupRelationEntity[] vos) {
        List<AttrAttrgroupRelationEntity> entites = Arrays.asList(vos);
        attrAttrgroupRelationDao.deleteRelation(entites);
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        // 1、查出当前分组的分类ID
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        // 2、查出当前分类下的所有分组
        List<AttrGroupEntity> groups = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", attrGroupEntity.getCatelogId()));
        List<Long> collect = groups.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        // 3、查出当前分类下所有分组关联的属性
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntitys = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attrIds = attrAttrgroupRelationEntitys.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        // 4、排除查出已被关联的当前分类下的基础属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()).eq("catelog_id",attrGroupEntity.getCatelogId());
        if(params.get("key") != null && !StringUtils.isEmpty(params.get("key"))){
            queryWrapper.and((qw)->{
                qw.eq("attr_id", params.get("key")).or().eq("attr_name",params.get("key"));
            });
        }
        if(attrIds != null && attrIds.size() > 0){
            queryWrapper.notIn("attr_id", attrIds);
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}