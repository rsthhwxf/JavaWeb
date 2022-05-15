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
import java.util.Date;

/**
 *
 * 商品详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Order implements Serializable {
    @TableId
    private Integer id;
    private double price;
    private int num;
    private Integer itemId;
    private Integer userId;
    private int status;
    private String item_name;
    private Integer manageId;
    private String image;
}
