package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.User;

import java.util.List;

public interface UserService extends IService<User> {
    public User getByUsername(String username);

}