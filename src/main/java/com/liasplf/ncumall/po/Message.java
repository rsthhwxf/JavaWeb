package com.liasplf.ncumall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message implements Serializable {

    private Integer id;
    /**
     * 姓名
     */
    private  String name;
    /**
     * 内容
     */
    private String content;

    /**
     * 手机号
     */
    private String phone;


}
