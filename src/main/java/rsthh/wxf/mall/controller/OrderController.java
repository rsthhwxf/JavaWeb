package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.Orders;
import rsthh.wxf.mall.po.OrderDetail;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.OrderService;
import rsthh.wxf.mall.service.OrderDetailService;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.JsonUtil;
import rsthh.wxf.mall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rsthh.wxf.mall.utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;


    @PostMapping("/list")
    public String myOrder(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap();
        User user = ThreadLocalUtil.getUser();
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        List<Orders> list = orderService.getOrderByUserId(user.getId(),pageNum,pageSize);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", list);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/detail")
    public String itemInfo(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        User user = ThreadLocalUtil.getUser();
        Map returnData = new HashMap();
        String orderDetailsID = (String) map.get("orderDetailsID");
        OrderDetail orderDetail = null;
        orderDetail = orderDetailService.getById(orderDetailsID);
        if (orderDetail != null) {
            if (orderDetail.getUserId()== user.getId()){
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

    @PostMapping
    public String receive(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        User user = ThreadLocalUtil.getUser();
        Map returnData = new HashMap();
        Integer orderID = (Integer) map.get("orderID");
        Orders order = orderService.getById(orderID);
        if(order==null){
            returnData.put("msg", "订单不存在!");
            returnData.put("status",1);
            returnData.put("data", "");
        }
        if(user.getId()==order.getUserId()){
            returnData.put("status",1);
            returnData.put("msg", "用户无权访问!");
            returnData.put("data", "");
        }
        orderService.updateStatus(orderID);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", "");
        return JsonUtil.toJson(returnData);
    }
}
