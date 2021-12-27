package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.ItemOrder;
import com.liasplf.ncumall.po.OrderDetail;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.*;
import com.liasplf.ncumall.utils.DataEcho;
import com.liasplf.ncumall.utils.JsonUtil;
import com.liasplf.ncumall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/order")
public class APPOrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private AppItemOrderService appitemOrderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ItemService itemService;


    @GetMapping("/all")
    public String myOrder(HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap();
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        User user = userService.getById(userID);
        if (user == null) {
            return DataEcho.NoAuthority();
        }
        List<ItemOrder> list = itemOrderService.getItemOrderByUserId(userID);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", list);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/details")
    public String itemInfo(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        User user = userService.getById(userID);
        if (user == null) {
            DataEcho.NoAuthority();
        }
        Map returnData = new HashMap();
        String orderDetailsID = (String) map.get("orderDetailsID");
        OrderDetail orderDetail = null;
        orderDetail = orderDetailService.getById(orderDetailsID);
        System.out.println(orderDetailsID);
        if (orderDetail != null) {
            if (appitemOrderService.getOrderByCode(orderDetail.getOrderId()).getUserId() == userID){
                returnData.put("status", 0);
                returnData.put("msg", "成功!");
                returnData.put("data", orderDetail);
            }
            else{
                returnData.put("status",1);
                returnData.put("msg", "用户无权访问!");
                returnData.put("data", "");
            }
        }
        else{
            returnData.put("msg", "订单不存在!");
            returnData.put("status",1);
            returnData.put("data", "");
        }
        return JsonUtil.toJson(returnData);
    }
}
