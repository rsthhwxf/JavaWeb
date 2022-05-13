package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private int id;
    private String username;
    private String password;
    private String phone;
    private String realName;
    private String sex;
    private String address;
    private String email;
    private int userType;
    private int isDelete;

}
