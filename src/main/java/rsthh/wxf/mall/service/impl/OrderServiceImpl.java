package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import rsthh.wxf.mall.dao.OrderDao;
import rsthh.wxf.mall.po.Orders;
import rsthh.wxf.mall.service.OrderService;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Orders> implements OrderService {

    @Override
    public List<Orders> getOrderByUserId(Integer id, int pageNum, int pageSize) {
//        System.out.println(pageNum+pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<Orders> list = this.baseMapper.selectList(new QueryWrapper<Orders>().eq("user_id",id));
        System.out.println(list);
        System.out.println("type"+ list.getClass());
        PageInfo<Orders> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

    @Override
    public void updateStatus(Integer id) {
        this.baseMapper.update(null,new UpdateWrapper<Orders>().set("status",3).eq("id",id));
    }
}
