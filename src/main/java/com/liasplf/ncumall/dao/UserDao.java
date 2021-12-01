package com.liasplf.ncumall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liasplf.ncumall.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
