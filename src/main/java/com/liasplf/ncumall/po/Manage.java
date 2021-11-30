package com.liasplf.ncumall.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manage implements Serializable {
    @TableId
    private Integer id;
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String passWord;
    @TableField("realName")
    private String realName;
    @TableField("status")
    private Integer status;

}
