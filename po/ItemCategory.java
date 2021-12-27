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
 * 类目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCategory implements Serializable {

    /**
     * 主键id
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 类目名称
     */
    @TableField("name")
    private String name;

    /**
     * 父id
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 是否已删除
     */
    @TableField("isDelete")
    private Integer isDelete;


}
