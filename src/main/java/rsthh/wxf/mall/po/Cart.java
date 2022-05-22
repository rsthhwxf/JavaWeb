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

/**
 * 购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Cart implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private int num;
    private double price;
    private Integer itemId;
    private Integer userId;
    private Integer manageId;
    private String image;
}
