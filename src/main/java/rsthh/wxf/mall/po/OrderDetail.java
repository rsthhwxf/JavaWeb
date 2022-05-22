package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class OrderDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private double price;
    private int num;
    private Integer itemId;
    private Integer userId;
    private String itemName;
    private Integer manageId;
    private String address;
    private String phone;
    private Date add_time;
    private String code;
    private String image;
}
