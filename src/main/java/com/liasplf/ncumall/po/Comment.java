package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @TableField("item_id")
    private Integer itemId;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 发布时间
     */
    @TableField("addTime")
    private Date addTime;



}
