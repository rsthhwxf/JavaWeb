package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.ItemOrderDao;
import com.liasplf.ncumall.dao.UserDao;
import com.liasplf.ncumall.po.ItemOrder;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("itemOrderServiceImpl")
public class ItemOrderServiceImpl extends ServiceImpl<ItemOrderDao,ItemOrder> implements ItemOrderService {
    @Autowired
    private UserDao userDao;

    Map<Integer,User> map = new HashMap<Integer,User>();

    @Override
    public List<ItemOrder> searchItemOrder(String code, Integer id) {
        List<ItemOrder> orderList;
        if(code==null){
            orderList = this.getBaseMapper().selectList(new QueryWrapper<ItemOrder>().eq("manage_id", id));
        }else{
            orderList = this.getBaseMapper().selectList(new QueryWrapper<ItemOrder>().eq("manage_id", id).like("code",code));
        }

        List<User> users = userDao.selectList(null);
        for (User user : users) {
            map.put(user.getId(),user);
        }

        for (ItemOrder itemOrder : orderList) {
            Integer uId = itemOrder.getUserId();
            itemOrder.setUser(map.get(uId));

            double total = Double.parseDouble(itemOrder.getTotal());
            itemOrder.setTotal(new Formatter().format("%.2f", total).toString());
        }
        return orderList;
    }
}
