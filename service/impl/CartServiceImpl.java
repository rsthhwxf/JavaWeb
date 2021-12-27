package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.CartDao;
import com.liasplf.ncumall.dao.ItemDao;
import com.liasplf.ncumall.po.Cart;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.service.CartService;
import com.liasplf.ncumall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CartService")
public class CartServiceImpl extends ServiceImpl<CartDao, Cart> implements CartService {
    @Autowired
    private ItemService itemService;
    @Override
    public List<Cart> getCartItems(Integer id) {
        List<Cart> carts = this.baseMapper.selectList(new QueryWrapper<Cart>().eq("user_id", id));
        for (Cart cart : carts) {
            Integer itemId = cart.getItemId();
            Item item = itemService.getById(itemId);
            cart.setItem(item);
        }
        return carts;
    }
}
