package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment implements Serializable {

    private Integer id;

    private Integer userId;

    private User user;

    private Integer itemId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date addTime;



}
