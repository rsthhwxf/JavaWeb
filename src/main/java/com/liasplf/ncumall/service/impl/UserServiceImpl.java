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
        return this.getBaseMapper().selectList(new QueryWrapper<User>());
    }

    @Override
    public List<User> searchUser(String userName) {
        return this.getBaseMapper().selectList(new QueryWrapper<User>().like("userName",userName));
    }
}
