package com.rs.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.rs.common.valid.AddGroup;
import com.rs.common.valid.ListValue;
import com.rs.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-24 16:00:50
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@NotNull(message="修改时品牌ID不能为空",groups = {UpdateGroup.class})
	@Null(message="新增时品牌ID必须为空",groups = {AddGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message="品牌名必须提交")
	private String name;
	/**
	 * 品牌logo地址
	 */
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(vals = {0,1}, groups = {AddGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-z]$", message = "检索首字母必须是一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value=0, message = "排序字段必须大于0")
	private Integer sort;

}
