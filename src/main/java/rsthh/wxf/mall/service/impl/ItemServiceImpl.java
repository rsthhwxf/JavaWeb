package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import rsthh.wxf.mall.dao.ItemDao;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.po.Orders;
import rsthh.wxf.mall.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemDao, Item> implements ItemService {



    @Override
    public List<Item> getItemList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Item> list =  this.baseMapper.selectList(new QueryWrapper<Item>().eq("isDelete",0));
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();

    }

    @Override
    public List<Item> searchItem(String keyword){

        List<Item> list = getItemList(10,10);
        List<Item> ans = new ArrayList<Item>();
        for (Item item : list) {
            if(item.getItemName().contains(keyword))ans.add(item);
        }
        return ans;
    }



}
