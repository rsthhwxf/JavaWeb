package com.liasplf.ncumall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrderDao extends BaseMapper<ItemOrder> {
}
