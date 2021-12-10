package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.OrderDetailDao;
import com.liasplf.ncumall.po.OrderDetail;
import com.liasplf.ncumall.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service("orderDetailService")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {
    @Override
    public OrderDetail getByOrderCode(String code) {
        return this.baseMapper.selectOne(new QueryWrapper<OrderDetail>().eq("order_id",code));
    }
}
