package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart{

    private int id;
    private String name;
    private int num;
    private double price;
    private int itemId;
    private int userId;
    private int manageId;
    private String image;
}
