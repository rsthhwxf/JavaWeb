package com.liasplf.ncumall.service;

import com.liasplf.ncumall.dao.AppCartDao;
import com.liasplf.ncumall.dao.AppItemDao;
import com.liasplf.ncumall.po.Cart;
import com.liasplf.ncumall.po.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppItemService {
    @Autowired
    private AppItemDao appItemDao;

    public List<Item> getItemList(){
        return appItemDao.getItemList();
    }

    public List<Item> searchItem(String itemName) {

        List<Item> list = getItemList();
        List<Item> ans = new ArrayList<Item>();
        for (Item item : list) {
            if(item.getName().contains(itemName))ans.add(item);
        }
        return ans;
    }
}
