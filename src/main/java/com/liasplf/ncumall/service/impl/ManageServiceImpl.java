package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.ManageDao;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ManageService;
import org.springframework.stereotype.Service;

@Service("manageService")
public class ManageServiceImpl extends ServiceImpl<ManageDao, Manage> implements ManageService {

}
