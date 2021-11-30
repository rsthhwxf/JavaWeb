package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 收藏
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sc implements Serializable {

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
     * 收藏者id
     */
    private Integer userId;


}
