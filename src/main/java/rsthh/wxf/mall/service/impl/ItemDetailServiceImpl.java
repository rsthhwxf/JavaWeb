package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rsthh.wxf.mall.dao.ItemDetailDao;
import rsthh.wxf.mall.po.ItemDetail;
import rsthh.wxf.mall.service.ItemDetailService;
import rsthh.wxf.mall.service.OrderDetailService;

@Service
public class ItemDetailServiceImpl extends ServiceImpl<ItemDetailDao, ItemDetail> implements ItemDetailService {
}
