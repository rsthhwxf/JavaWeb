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
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Item implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String itemName;
    private double price;
    private Integer manageId;
    private String manageName;
    private String image;
}
