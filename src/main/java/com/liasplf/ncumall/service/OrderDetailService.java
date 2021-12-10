package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.OrderDetail;

public interface OrderDetailService extends IService<OrderDetail> {
    OrderDetail getByOrderCode(String code);
}
