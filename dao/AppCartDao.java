/*
author:wxf
 */
package com.liasplf.ncumall.dao;

import com.liasplf.ncumall.po.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCartDao {

    @Select("select * from cart where item_id = #{itemID} and user_id=#{userID}")
    public Cart getCartByItem(@Param("itemID") Integer itemID, @Param("userID") Integer userID);

    @Update("update cart set num = #{num},total=#{total} where item_id=#{itemID}")
    public void updateCartNum(@Param("itemID") Integer itemID, @Param("num") Integer num,@Param("total")String total);
}
