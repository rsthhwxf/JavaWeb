package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.ItemOrder;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ItemOrderService;
import com.liasplf.ncumall.service.ManageService;
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
@RequestMapping("/app/store/order")
public class AppStoreOrderController {
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private ManageService manageService;

    @GetMapping("/orderList")
    public String listItemOrder(HttpServletRequest request) throws Exception {
        Integer manageID = TokenUtil.getIDByRequest(request);
        Manage manage = manageService.getById(manageID);
        if (manage == null) {
            DataEcho.NoAuthority();
        }
        List<ItemOrder> itemOrderList = itemOrderService.getItemOrderByManageID(manageID);
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemOrderList);
        return JsonUtil.toJson(returnData);
    }

    @RequestMapping("/send")
    public String send(HttpServletRequest httpServletRequest, @RequestBody Map map) throws Exception {
        Manage manage = manageService.getById(TokenUtil.getIDByRequest(httpServletRequest));
        if (manage == null) {
            DataEcho.NoAuthority();
        }
        Integer orderID = (Integer) map.get("orderID");
        ItemOrder order = itemOrderService.getById(orderID);
        order.setStatus(2);
        itemOrderService.updateById(order);
        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/search")
    public String searchOrder(HttpServletRequest httpServletRequest, @RequestBody Map map) throws Exception {
        Map returnData = new HashMap();
        Manage manage = manageService.getById(TokenUtil.getIDByRequest(httpServletRequest));
        if (manage == null) {
            DataEcho.NoAuthority();
        }
        String orderCode = (String) map.get("orderID");
        List<ItemOrder> itemOrderList = itemOrderService.searchItemOrder(orderCode,manage.getId());
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemOrderList);
        return JsonUtil.toJson(returnData);
    }

}
