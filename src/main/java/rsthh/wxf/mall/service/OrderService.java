package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    public List<Order> getOrderByUserId(Integer id,int pageNum,int pageSize);

    public void updateStatus(Integer id);
}
