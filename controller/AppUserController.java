package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.ItemOrderService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.OrderDetailService;
import com.liasplf.ncumall.service.UserService;
import com.liasplf.ncumall.utils.DataEcho;
import com.liasplf.ncumall.utils.JsonUtil;
import com.liasplf.ncumall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/user")
public class AppUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemOrderService itemOrderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/login")
    public String UserLogin(@RequestBody Map map) {
        Map returnData = new HashMap<>();
        String username = (String) map.get("userName");
        String password = (String) map.get("password");
        User user = userService.getByUserName(username);

        if ("".equals(username) || "".equals(password)) {
            returnData.put("status", 1);
            returnData.put("msg", "用户名或密码为空!");
            returnData.put("data", "");
        } else if (!"".equals(username) && user.getPassword().equals(password)) {
            String token = TokenUtil.getToken(user);
            returnData.put("status", 0);
            returnData.put("msg", "成功!");
            returnData.put("data", token);
        } else {
            returnData.put("status", 1);
            returnData.put("msg", "用户名或密码错误!");
            returnData.put("data", "");
        }
        return JsonUtil.toJson(returnData);
    }

    //注册映射
    @PostMapping("/register")
    public String UserRegister(@RequestBody User user) {
        Map returnData = new HashMap<>();
        if (userService.checkUser(user.getUserName())) {
            userService.save(user);
            DataEcho.NoDataSuccess();
        }
        returnData.put("status", 1);
        returnData.put("msg", "用户名已存在！");
        returnData.put("data", "");
        return JsonUtil.toJson(returnData);
    }

    @GetMapping("/info")
    public String UserInfo(HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        if (userID == null) {
            return DataEcho.NoAuthority();
        }
        User user = null;
        user = userService.getById(userID);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", user);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/updateInfo")
    public String updateInfo(@RequestBody User user, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        if (userID == null) {
            return DataEcho.NoAuthority();
        }
        userService.updateById(user);
        return DataEcho.NoDataSuccess();
    }


}
