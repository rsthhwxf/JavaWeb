package rsthh.wxf.mall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ES {
    private Integer id;
    private String itemName;
    private double price;
    private Integer manageId;
    private String manageName;
    private String image;
}
