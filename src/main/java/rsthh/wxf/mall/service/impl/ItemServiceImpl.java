package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import rsthh.wxf.mall.dao.ItemDao;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemDao, Item> implements ItemService {

    @Override
    public List<Item> getItemList(){
        return this.baseMapper.selectList(new QueryWrapper<Item>().eq("isDelete",0));
    }

    @Override
    public List<Item> searchItem(String keyword){

        List<Item> list = getItemList();
        List<Item> ans = new ArrayList<Item>();
        for (Item item : list) {
            if(item.getItemName().contains(keyword))ans.add(item);
        }
        return ans;
    }



}
