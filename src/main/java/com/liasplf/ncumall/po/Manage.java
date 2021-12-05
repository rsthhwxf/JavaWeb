package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manage implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String passWord;
    @TableField("realName")
    private String realName;
    @TableField("status")
    private Integer status;
    @TableField("isDelete")
    private Integer isDelete;
    @TableField("shopName")
    private String shopName;
    @TableField("createTime")
    private Date createTime;
    @TableField("description")
    private String description;
}
