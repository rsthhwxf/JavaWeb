package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ManageService;
import com.liasplf.ncumall.utils.DataEcho;
import com.liasplf.ncumall.utils.JsonUtil;
import com.liasplf.ncumall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/manage")
public class AppManageController {

    @Autowired
    private ManageService manageService;


    @PostMapping("/login")
    public String UserLogin(@RequestBody Map map) {
        Map returnData = new HashMap<>();
        String username = (String) map.get("userName");
        String password = (String) map.get("password");
        Manage manage = manageService.searchByName(username);
        if ("".equals(username) || "".equals(password)) {
            returnData.put("status", 1);
            returnData.put("msg", "商家名或密码为空!");
            returnData.put("data", "");
        } else if (!"".equals(username) && manage.getPassword().equals(password)) {
            String token = TokenUtil.getManageToken(manage);
            returnData.put("status", 0);
            returnData.put("msg", "成功!");
            returnData.put("data", token);
        } else {
            returnData.put("status", 1);
            returnData.put("msg", "商家名或密码错误!");
            returnData.put("data", "");
        }
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/register")
    public String manageLogin(@RequestBody Manage manage){
        Map returnData = new HashMap<>();
        Manage manage1 = manageService.searchByName(manage.getUserName());
        if (manage1==null){
            manage.setCreateTime(new Date());
            manageService.save(manage);
            returnData.put("status", 0);
            returnData.put("msg", "成功");
            returnData.put("data", "");
        }else{
            returnData.put("status", 1);
            returnData.put("msg", "商家名已存在!");
            returnData.put("data", "");
        }
        return JsonUtil.toJson(returnData);
    }

    @GetMapping("/info")
    public String manageInfo(HttpServletRequest request) throws Exception {
        Integer manageID = TokenUtil.getIDByRequest(request);
        Manage manage = manageService.getById(manageID);
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", manage);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("updateInfo")
    public String manageInfoUpdate(@RequestBody Manage manage, HttpServletRequest request) throws Exception {
        Integer manageID = TokenUtil.getIDByRequest(request);
        Manage manage2 = manageService.getById(manageID);
        if (manage2 == null) {
            DataEcho.NoAuthority();
        }
        manageService.updateById(manage);
        Map returnData = new HashMap();
        return DataEcho.NoDataSuccess();
    }
}
