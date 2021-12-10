package com.liasplf.ncumall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.liasplf.ncumall.po.Cart;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.CartService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class UserCartController{
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/myCart")
    public String myCart(Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login/toLogin";
        }

        List<Cart> list = cartService.getCartItems(user.getId());

        model.addAttribute("list",list);
        return "front/cart";
    }

    @RequestMapping("/cartAddItem")
    @ResponseBody
    public String cartAddItem(Integer itemId,Integer num,Model model,HttpServletRequest request){
        JSONObject js = new JSONObject();
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toJSONString();
        }
        //保存到购物车
        Integer userId = Integer.valueOf(attribute.toString());
        Cart cart = new Cart();
        cart.setItemId(itemId);
        cart.setUserId(userId);
        Item item = itemService.getById(itemId);

        String price = item.getPrice();
        Double valueOf = Double.valueOf(price);
        cart.setPrice(valueOf);
        if(item.getZk()!=null){
            valueOf = valueOf*item.getZk()/10;
            BigDecimal bg = new BigDecimal(valueOf).setScale(2, RoundingMode.UP);
            cart.setPrice(bg.doubleValue());
            valueOf = bg.doubleValue();
        }
        cart.setNum(num);
        cart.setTotal(""+num*valueOf);
        cartService.save(cart);
        js.put(Consts.RES,1);
        return js.toJSONString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String id){
        System.out.println(id);
        cartService.removeById(id);
    }
}
