package com.rs.gulimall.product.service.impl;

import com.rs.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.common.utils.PageUtils;
import com.rs.common.utils.Query;

import com.rs.gulimall.product.dao.CategoryDao;
import com.rs.gulimall.product.entity.CategoryEntity;
import com.rs.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> listTree = categoryEntities.stream().filter(
                entities -> entities.getParentCid() == 0
        ).map((entities) -> {
            entities.setChildren(getChildren(entities, categoryEntities));
            return entities;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return listTree;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> catelogPaths = new ArrayList<>();
        this.findParentId(catelogId, catelogPaths);
        Collections.reverse(catelogPaths);
        return catelogPaths.toArray(new Long[catelogPaths.size()]);
    }

    @Transactional
    @Override
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if(!StringUtils.isEmpty(category.getName())){
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    private void findParentId(Long catelogId, List<Long> catelogPaths){
        catelogPaths.add(catelogId);
        CategoryEntity categoryEntity = this.getById(catelogId);
        if(categoryEntity.getParentCid() != 0){
            findParentId(categoryEntity.getParentCid(), catelogPaths);
        }
    }

    // 递归查找所有菜单的子菜单
    public List<CategoryEntity> getChildren(CategoryEntity entity, List<CategoryEntity> all) {
        List<CategoryEntity> chaildren = all.stream().filter(
            ent -> {
                return entity.getCatId().equals(ent.getParentCid());
            }
        ).map((ent) -> {
            ent.setChildren(getChildren(ent, all));
            return ent;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return chaildren;
    }

}