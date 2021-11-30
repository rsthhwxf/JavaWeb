package com.liasplf.ncumall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.ItemCategoryDao;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.service.ItemCategoryService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemCategoryService")
public class ItemCategoryServiceImpl extends ServiceImpl<ItemCategoryDao, ItemCategory> implements ItemCategoryService {
    @Autowired
    private ItemCategoryDao mapper;



    @Override
    public List<ItemCategory> getAll() {
        return mapper.selectList(new QueryWrapper<ItemCategory>().eq("pid",0));
    }

}
