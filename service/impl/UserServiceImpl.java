package com.liasplf.ncumall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liasplf.ncumall.dao.UserDao;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public List<User> getAll() {
        return this.getBaseMapper().selectList(new QueryWrapper<User>().eq("isDelete",0));
    }

    @Override
    public List<User> searchUser(String userName) {
        return this.getBaseMapper().selectList(new QueryWrapper<User>().eq("isDelete",0).like("userName",userName));
    }

    @Override
    public User getByUserName(String userName) {
        User user = this.baseMapper.selectOne(new QueryWrapper<User>().eq("userName", userName));
        return user;
    }

    @Override
    public boolean checkUser(String userName) {
        User user = this.baseMapper.selectOne(new QueryWrapper<User>().eq("userName", userName));
        if(user==null)return true;
        return false;
    }
}
