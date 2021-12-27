/*
author:wxf
 */
package com.liasplf.ncumall.service;

import com.liasplf.ncumall.dao.AppCartDao;
import com.liasplf.ncumall.po.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppCartService {

    @Autowired
    private AppCartDao appCartDao;
    public Cart getCartByItem(Integer itemID,Integer userID){
        return appCartDao.getCartByItem(itemID,userID);
    }

    public void updateCartNum(Integer itemID,Integer num,String total){
        appCartDao.updateCartNum(itemID,num,total);
    }
}
