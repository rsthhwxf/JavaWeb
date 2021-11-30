package com.liasplf.ncumall.po;

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
public class Car implements Serializable {

    private Integer id;
    /**
     * 商品id
     */
    private Integer itemId;
    /**
     * 商品对象
     */
    private Item item;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品单价
     */
    private double price;

    /**
     * 商品总价
     */
    private String total;


}
