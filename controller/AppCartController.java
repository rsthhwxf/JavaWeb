package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.*;
import com.liasplf.ncumall.service.*;
import com.liasplf.ncumall.utils.DataEcho;
import com.liasplf.ncumall.utils.JsonUtil;
import com.liasplf.ncumall.utils.TokenUtil;
import com.liasplf.ncumall.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/cart")
public class AppCartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private AppCartService appCartService;
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    @GetMapping("/myCart")
    public String myCart(HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = null;
        userID = TokenUtil.getIDByRequest(httpServletRequest);
        List<Cart> cartList = cartService.getCartItems(userID);
        Map data = new HashMap<>();
        data.put("cartList", cartList);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", data);
        return JsonUtil.toJson(returnData);
    }


    @PostMapping("/add")
    public String addItem(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = null;
        userID = TokenUtil.getIDByRequest(httpServletRequest);
        Integer itemID = (Integer) map.get("itemID");
        Item item = null;
        item = itemService.getById(itemID);
        if (item == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        Cart cart = appCartService.getCartByItem(item.getId(), userID);
        if (cart != null) {
            String total = String.valueOf(cart.getPrice() * (cart.getNum() + 1));
            appCartService.updateCartNum(itemID, cart.getNum() + 1,total);
        } else {
            Cart cart1 = new Cart();
            cart1.setNum(1);
            cart1.setPrice(Double.parseDouble(item.getPrice()));
            cart1.setTotal(String.valueOf(cart1.getPrice() * cart1.getNum()));
            cart1.setItem(item);
            cart1.setItemId(item.getId());
            cart1.setUserId(userID);
            cartService.save(cart1);
        }

        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = null;
        userID = TokenUtil.getIDByRequest(httpServletRequest);
        Integer itemID = (Integer) map.get("itemID");
        Item item = itemService.getById(itemID);
        if (item == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        Cart cart = appCartService.getCartByItem(item.getId(), userID);
        Integer num = cart.getNum();
        if (num == 1) {
            cartService.removeById(cart.getId());
        }
        else{
            String total = String.valueOf(cart.getPrice() * (num - 1));
            appCartService.updateCartNum(itemID, num - 1,total);
        }
        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/buy")
    public String exAdd(@RequestBody List<Integer> list, HttpServletRequest request) throws Exception {
        Map returnData = new HashMap();
        Integer userID = TokenUtil.getIDByRequest(request);
        User user = userService.getById(userID);
        if (StringUtils.isEmpty(user.getAddress())) {
            returnData.put("status", "1");
            returnData.put("msg", "请填写收货地址!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }

        for (Integer cartID : list) {
            Cart cart = cartService.getById(cartID);
            if (cart == null) continue;
            Integer itemID = cart.getItemId();
            Item item = itemService.getById(itemID);

            ItemOrder order = new ItemOrder();
            order.setItemId(itemID);
            order.setUserId(user.getId());
            order.setCode(UUIDUtils.create());
            order.setAddTime(new Date());
            order.setTotal(cart.getTotal());
            order.setIsDelete(0);
            order.setStatus(0);
            order.setManageId(item.getManageId());
            itemOrderService.save(order);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setItemId(itemID);
            orderDetail.setOrderId(order.getCode());
            orderDetail.setStatus(0);
            orderDetail.setNum(cart.getNum());
            orderDetail.setTotal(cart.getTotal());
            orderDetailService.save(orderDetail);
            item.setGmNum(item.getGmNum() + cart.getNum());
            itemService.updateById(item);
            cartService.removeById(cartID);

        }
        return DataEcho.NoDataSuccess();

    }


}
