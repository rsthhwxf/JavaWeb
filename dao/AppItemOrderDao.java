package com.liasplf.ncumall.dao;

import com.liasplf.ncumall.po.ItemOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface AppItemOrderDao {
    @Select("select * from item_order where code=#{code}")
    public ItemOrder getOrderByCode(@Param("code")String code);
}
