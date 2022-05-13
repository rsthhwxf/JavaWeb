package rsthh.wxf.mall.po;

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
 *
 * 商品详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order{
    private int id;
    private double price;
    private int num;
    private int itemId;
    private int userId;
    private int status;
    private String item_name;
    private int manageId;
    private String image;
}
