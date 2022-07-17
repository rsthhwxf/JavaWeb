package rsthh.wxf.mall.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {
    private String keyword;//全文传递过来的检索关键字
    private String sort;//排序的条件
    /**
     * skuPrice区间（1-500）-（null-500）-(500-null)、
     */
    private String skuPrice;
    //页码
    private Integer pageNum;
}
