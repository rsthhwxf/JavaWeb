package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 折扣
     */
    private Integer zk;

    /**
     * 收藏数
     */
    private Integer scNum;

    /**
     * 购买数
     */
    private Integer gmNum;

    /**
     * 主图
     */
    private String url1;

    /**
     * 副图1
     */
    private String url2;

    /**
     * 副图2
     */
    private String url3;

    /**
     * 副图3
     */
    private String url4;

    /**
     * 副图4
     */
    private String url5;

    /**
     * 描述
     */
    private String ms;

    private String pam1;
    private String pam2;
    private String pam3;
    private String val1;
    private String val2;
    private String val3;

    private Integer type;

    /**
     * 类别id一级
     */
    private Integer categoryIdOne;

    private ItemCategory yiji;

    /**
     * 类别id二级
     */
    private Integer categoryIdTwo;

    private ItemCategory erji;

    /**
     * 是否有效 0有效 1已删除
     */
    private Integer isDelete;

    /**
     * 评论列表
     */
    private List<Comment> pls;


}
