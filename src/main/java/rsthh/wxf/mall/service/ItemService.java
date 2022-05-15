package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Item;

import java.util.List;


public interface ItemService extends IService<Item> {

    public List<Item> searchItem(String keyword);


    public List<Item> getItemList();

}
