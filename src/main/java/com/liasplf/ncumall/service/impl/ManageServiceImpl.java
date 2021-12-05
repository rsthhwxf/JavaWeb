package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.ManageDao;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ManageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("manageService")
public class ManageServiceImpl extends ServiceImpl<ManageDao, Manage> implements ManageService {

    @Override
    public List<Manage> getManagers(String shopName) {
        if(shopName==null){
            return this.baseMapper.selectList(new QueryWrapper<Manage>().eq("isDelete",0));
        }else{
            return this.baseMapper.selectList(new QueryWrapper<Manage>().eq("isDelete",0).like("shopName",shopName));
        }
    }
}
