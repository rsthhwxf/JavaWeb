package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    /**
     * 商品id
     */
    @TableField("item_id")
    private Integer itemId;
    /**
     * 商品对象
     */
    @TableField(exist = false)
    private Item item;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 商品数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 商品单价
     */
    @TableField("price")
    private double price;

    /**
     * 商品总价
     */
    @TableField("total")
    private String total;


}
