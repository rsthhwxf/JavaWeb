package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item implements Serializable{
    /**
     * 主键
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 商品价格
     */
    @TableField("price")
    private String price;

    /**
     * 折扣
     */
    @TableField("zk")
    private Integer zk;

    /**
     * 收藏数
     */
    @TableField("scNum")
    private Integer scNum;

    /**
     * 购买数
     */
    @TableField("gmNum")
    private Integer gmNum;

    /**
     * 主图
     */
    @TableField("url1")
    private String url1;

    /**
     * 副图1
     */
    @TableField("url2")
    private String url2;

    /**
     * 副图2
     */
    @TableField("url3")
    private String url3;

    /**
     * 副图3
     */
    @TableField("url4")
    private String url4;

    /**
     * 副图4
     */
    @TableField("url5")
    private String url5;

    /**
     * 描述
     */
    @TableField("ms")
    private String describes;


    @TableField("type")
    private Integer type;

    /**
     * 类别id一级
     */
    @TableField("category_id_one")
    private Integer categoryIdOne;

    @TableField(exist = false)
    private ItemCategory yiji;

    /**
     * 类别id二级
     */
    @TableField("category_id_two")
    private Integer categoryIdTwo;
    @TableField(exist = false)
    private ItemCategory erji;

    /**
     * 是否有效 0有效 1已删除
     */
    @TableField("isDelete")
    private Integer isDelete;

    @TableField("manage_id")
    private Integer manageId;

    /**
     * 评论列表
     */
    @TableField(exist = false)
    private List<Comment> comments;


}
