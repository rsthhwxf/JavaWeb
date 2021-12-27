package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemOrder;

import java.util.List;

public interface ItemService extends IService<Item> {
    List<Item> getAll(Integer status, Integer id);

    List<Item> searchItem(String itemName, Integer status, Integer id);
    //    author:wxf
    List<Item> searchItem(String keyword, Integer id);
    //    author:wxf
    List<Item> getItemByManageID(Integer id);

    List<Item> getItemByManageId(Integer id);

    List<Item> getTopZk();

    List<Item> getTopRx();

    List<Item> getItemsByCat2(Integer categoryId2,Integer condition);
}
