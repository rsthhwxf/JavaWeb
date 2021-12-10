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
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @TableField("item_id")
    private Integer itemId;

    @TableField(exist = false)
    private Item item;

    /**
     * 订单id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 0.未退货 1已退货
     */
    @TableField("status")
    private Integer status;

    /**
     * 数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 小计
     */
    @TableField("total")
    private String total;


}
