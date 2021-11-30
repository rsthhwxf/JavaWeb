package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.liasplf.ncumall.po.ItemCategory;

import java.util.List;

public interface ItemCategoryService extends IService<ItemCategory> {

    List<ItemCategory> getAll();
}
