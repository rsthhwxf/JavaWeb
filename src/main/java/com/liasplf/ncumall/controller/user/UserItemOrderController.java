package com.liasplf.ncumall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.liasplf.ncumall.po.*;
import com.liasplf.ncumall.service.*;
import com.liasplf.ncumall.utils.Consts;
import com.liasplf.ncumall.utils.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/itemOrder")
public class UserItemOrderController {
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;


    @RequestMapping("/jiesuan")
    @ResponseBody
    public String exAdd(@RequestBody List<CartDto> list, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        JSONObject js = new JSONObject();
        if(user==null){
            js.put(Consts.RES,0);
            return js.toJSONString();
        }
        if(StringUtils.isEmpty(user.getAddress())){
            js.put(Consts.RES,2);
            return js.toJSONString();
        }

        for(CartDto c:list){
            BigDecimal to = new BigDecimal(0);

            Cart cart = cartService.getById(c.getId());
            Integer itemId = cart.getItemId();
            Item item = itemService.getById(itemId);
            to = to.add(new BigDecimal(cart.getPrice()).multiply(new BigDecimal(c.getNum())));

            ItemOrder order = new ItemOrder();


            order.setItemId(itemId);
            order.setUserId(user.getId());
            order.setCode(UUIDUtils.create());
            order.setAddTime(new Date());
            order.setTotal(to.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            order.setIsDelete(0);
            order.setStatus(0);
            order.setManageId(item.getManageId());
            itemOrderService.save(order);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setItemId(itemId);
            orderDetail.setOrderId(order.getCode());
            orderDetail.setStatus(0);
            orderDetail.setNum(c.getNum());
            orderDetail.setTotal(to.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            orderDetailService.save(orderDetail);

            item.setGmNum(item.getGmNum()+c.getNum());
            itemService.updateById(item);
            cartService.removeById(c.getId());

        }

        js.put(Consts.RES,1);
        return js.toJSONString();
    }
    private static String date;
    private static long orderNum = 0L;
    public static synchronized String getOrderNo(){
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
            orderNum = 0L;
        }
        orderNum++;
        long orderNO = Long.parseLong(date)*10000;
        orderNO += orderNum;
        return orderNO+"";
    }

    @RequestMapping("/cancel")
    public String cancel(Integer id){
        ItemOrder itemOrder = itemOrderService.getById(id);
        itemOrder.setStatus(1);
        String code = itemOrder.getCode();
        OrderDetail orderDetail = orderDetailService.getByOrderCode(code);
        orderDetail.setStatus(1);

        itemOrderService.updateById(itemOrder);
        orderDetailService.updateById(orderDetail);
        return "redirect:/user/myOrder";
    }

    @RequestMapping("/waitGet")
    public String waitGet(Integer id){
        ItemOrder itemOrder = itemOrderService.getById(id);
        itemOrder.setStatus(3);
        String code = itemOrder.getCode();
        OrderDetail orderDetail = orderDetailService.getByOrderCode(code);
        orderDetail.setStatus(3);

        itemOrderService.updateById(itemOrder);
        orderDetailService.updateById(orderDetail);
        return "redirect:/user/myOrder";
    }

    @RequestMapping("/toComment")
    public String toComment(Integer id, Model model){
        model.addAttribute("id",id);
        return "user/toComment";
    }
}
