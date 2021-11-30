package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 商品详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    private Item item;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 0.未退货 1已退货
     */
    private Integer status;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 小计
     */
    private String total;


}
