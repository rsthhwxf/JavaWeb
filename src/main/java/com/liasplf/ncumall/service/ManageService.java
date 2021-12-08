package com.liasplf.ncumall.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.Manage;

import java.util.List;

public interface ManageService extends IService<Manage> {

    List<Manage> getManagers(String shopName);

    Manage searchByName(String userName);
}
