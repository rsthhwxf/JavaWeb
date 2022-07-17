package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.vo.SearchParam;
import rsthh.wxf.mall.vo.SearchResult;

import java.util.List;


public interface ItemService extends IService<Item> {



    public SearchResult searchItem(SearchParam searchParam);


    public List<Item> getItemList(int pageNum, int pageSize);

}
