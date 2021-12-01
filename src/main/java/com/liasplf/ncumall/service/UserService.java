package com.liasplf.ncumall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liasplf.ncumall.po.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getAll();

    List<User> searchUser(String userName);
}
