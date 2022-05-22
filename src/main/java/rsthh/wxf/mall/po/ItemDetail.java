package rsthh.wxf.mall.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ItemDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer manageId;
    private String name;
    private double price;
    private String url1;
    private String url2;
    private String url3;
    private String url4;
    private String url5;
    private String info;
    private int isDelete;
    private List<Comment> comments;
}
