package com.rs.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.common.utils.PageUtils;
import com.rs.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author rs
 * @email rs@gmail.com
 * @date 2020-09-25 14:44:59
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

