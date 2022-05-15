package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import rsthh.wxf.mall.dao.UserDao;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;

    public User getByUsername(String username){
        return userDao.selectOne(new QueryWrapper<User>().eq("username",username));
    }
}
