package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import rsthh.wxf.mall.dao.UserDao;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    public User getByUsername(String username){
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }
}
