package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.ItemOrder;

import java.util.List;

public interface ItemOrderService extends IService<ItemOrder> {


    List<ItemOrder> searchItemOrder(String code, Integer id);

}
