package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemOrder implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 购买者id
     */
    private Integer userId;

    private User user;

    /**
     * 订单号
     */
    private String code;

    /**
     * 购买时间
     */
    private Date addTime;

    /**
     * 购买数量
     */
    private String total;

    private Integer isDelete;

    /**
     * 0.新建待发货1.已取消 2已发货3.到收货4已评价
     */
    private Integer status;

    private List<OrderDetail> details;


}
