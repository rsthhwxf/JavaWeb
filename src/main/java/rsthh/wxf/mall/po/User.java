package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class User implements Serializable {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String realname;
    private String sex;
    private String address;
    private String email;
    private int userType;
    private int isDelete;

}
