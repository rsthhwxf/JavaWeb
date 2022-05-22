package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * 商品详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Orders implements Serializable {
    @TableId(type = IdType.AUTO)
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
