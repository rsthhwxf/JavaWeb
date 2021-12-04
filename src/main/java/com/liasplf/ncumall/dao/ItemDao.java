package com.liasplf.ncumall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liasplf.ncumall.po.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends BaseMapper<Item> {
}
