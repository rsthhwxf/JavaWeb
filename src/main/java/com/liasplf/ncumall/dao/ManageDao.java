package com.liasplf.ncumall.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liasplf.ncumall.po.Manage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员
 */
@Mapper
public interface ManageDao extends BaseMapper<Manage> {
}
