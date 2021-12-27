package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.ItemOrder;

import java.util.List;

public interface ItemOrderService extends IService<ItemOrder> {

//    author:wxf
    List<ItemOrder> searchItemOrder(String code, Integer id);

    List<ItemOrder> getItemOrderByUserId(Integer id);

    public List<ItemOrder> getItemOrderByManageID(Integer id);
}
