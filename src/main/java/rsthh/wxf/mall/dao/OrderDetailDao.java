package rsthh.wxf.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import rsthh.wxf.mall.po.Order;
import rsthh.wxf.mall.po.OrderDetail;

@Repository
public interface OrderDetailDao extends BaseMapper<OrderDetail> {
}