package rsthh.wxf.mall.vo;

import lombok.Data;
import rsthh.wxf.mall.po.Item;

import java.util.List;

@Data
public class SearchResult {
    //查询到的所有商品信息
    private List<Item> products;
    //分页信息
    private Integer pageNum;//当前页码
    private Long total;//总记录数
    private Integer totalPages;//总页码
    private List<Integer> pageNavs;
}
