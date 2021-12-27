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
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    /**
     * 主键id
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("userName")
    private String userName;

    /**
     * 密码
     */
    @TableField("passWord")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField("realName")
    private String realName;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;

    @TableField("status")
    private Integer status;

    @TableField("isDelete")
    private Integer isDelete;
}
