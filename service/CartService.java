package com.liasplf.ncumall.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {
    List<Cart> getCartItems(Integer id);
}
