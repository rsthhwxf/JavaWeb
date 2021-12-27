package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
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
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @TableField("item_id")
    private Integer itemId;

    /**
     * 购买者id
     */
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    /**
     * 订单号
     */
    @TableField("code")
    private String code;

    /**
     * 购买时间
     */
    @TableField("addTime")
    private Date addTime;

    /**
     * 购买数量
     */
    @TableField("total")
    private String total;

    @TableField("isDelete")
    private Integer isDelete;

    /**
     * 0.新建待发货1.已取消 2已发货3.到收货4已评价
     */
    @TableField("status")
    private Integer status;

    @TableField("manage_id")
    private Integer manageId;

    @TableField(exist = false)
    private List<OrderDetail> details = new ArrayList<>();


}
