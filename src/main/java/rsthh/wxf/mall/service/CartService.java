package rsthh.wxf.mall.service;


import com.baomidou.mybatisplus.extension.service.IService;
import rsthh.wxf.mall.po.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {
    public List<Cart> getCarts(Integer userID);

    public Cart getCartByItem(Integer itemID, Integer userID);

    public void updateCartNum(Integer itemID,int num);
}
