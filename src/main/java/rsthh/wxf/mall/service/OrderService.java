package rsthh.wxf.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Orders;

import java.util.List;

public interface OrderService extends IService<Orders> {

    public List<Orders> getOrderByUserId(Integer id, int pageNum, int pageSize);

    public void updateStatus(Integer id);
}
