package com.liasplf.ncumall.dao;

import com.liasplf.ncumall.po.Cart;
import com.liasplf.ncumall.po.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppItemDao {

    @Select("select * from item")
    public List<Item> getItemList() ;
}
