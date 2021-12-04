package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.Item;

import java.util.List;

public interface ItemService extends IService<Item> {
    List<Item> getAll(Integer status, Integer id);

    List<Item> searchItem(String itemName, Integer status, Integer id);
}
