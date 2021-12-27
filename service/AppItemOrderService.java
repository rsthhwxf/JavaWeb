package com.liasplf.ncumall.service;

import com.liasplf.ncumall.dao.AppItemOrderDao;
import com.liasplf.ncumall.po.ItemOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppItemOrderService {

    @Autowired
    private AppItemOrderDao appItemOrderDao;

    public ItemOrder getOrderByCode(String code){
        return appItemOrderDao.getOrderByCode(code);
    }
}
