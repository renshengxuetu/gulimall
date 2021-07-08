package com.rs.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.rs.common.valid.AddGroup;
import com.rs.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rs.gulimall.product.entity.BrandEntity;
import com.rs.gulimall.product.service.BrandService;
import com.rs.common.utils.PageUtils;
import com.rs.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:40:36
 *
 * JSR303 校验前端提交的数据
 *  1). 给 entity 类的属性加上校验注解: javax.validation.constraints 下的类
 *  2). 给 controller 类的接口方法参数加上 @Valid 注解
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save (@Validated({AddGroup.class}) @RequestBody BrandEntity brand/*, BindingResult result*/){
        /*
        if(result.hasErrors()){
            Map<String,String> map = new HashMap<>();
            result.getFieldErrors().forEach((item) -> {
                // 获取到错误提示
                String message = item.getDefaultMessage();
                // 获取错误的属性的名字
                String field = item.getField();
                map.put(field, message);
            });
            return R.error(400,"提交的数据不合法").put("data",map);
        }else{

        }
        */
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
