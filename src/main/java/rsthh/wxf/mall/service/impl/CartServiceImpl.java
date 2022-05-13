package rsthh.wxf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import rsthh.wxf.mall.dao.CartDao;
import rsthh.wxf.mall.po.Cart;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.service.CartService;
import rsthh.wxf.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartDao, Cart> implements CartService {
    @Autowired
    private ItemService itemService;
    @Override
    public List<Cart> getCarts(Integer userID) {
        List<Cart> carts = this.baseMapper.selectList(new QueryWrapper<Cart>().eq("user_id", userID));
        return carts;
    }

    @Override
    public Cart getCartByItem(Integer itemID, Integer userID) {
        return this.getBaseMapper().selectOne(new QueryWrapper<Cart>().eq("item_id",itemID).eq("user_id",userID));
    }

    @Override
    public void updateCartNum(Integer itemID,int num) {
        this.getBaseMapper().update(null,new UpdateWrapper<Cart>().set("num",num).eq("num",num).eq("item_id",itemID));
    }

}
