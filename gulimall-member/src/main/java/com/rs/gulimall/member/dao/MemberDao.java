package com.rs.gulimall.member.dao;

import com.rs.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:44:59
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
