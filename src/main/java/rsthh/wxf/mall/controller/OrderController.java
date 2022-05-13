package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.Order;
import rsthh.wxf.mall.po.OrderDetail;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.OrderService;
import rsthh.wxf.mall.service.ItemService;
import rsthh.wxf.mall.service.OrderDetailService;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.JsonUtil;
import rsthh.wxf.mall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ItemService itemService;


    @GetMapping("/list")
    public String myOrder(HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap();
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        List<Order> list = orderService.getOrderByUserId(userID);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", list);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/detail")
    public String itemInfo(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        User user = userService.getById(userID);
        Map returnData = new HashMap();
        String orderDetailsID = (String) map.get("orderDetailsID");
        OrderDetail orderDetail = null;
        orderDetail = orderDetailService.getById(orderDetailsID);
        if (orderDetail != null) {
            if (orderDetail.getUserId()== userID){
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
