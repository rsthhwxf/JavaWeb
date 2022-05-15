package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.DataEcho;
import rsthh.wxf.mall.utils.JsonUtil;
import rsthh.wxf.mall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public String UserLogin(@RequestBody Map map) {
        Map returnData = new HashMap<>();
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        if ("".equals(username) || "".equals(password)) {
            returnData.put("status", 1);
            returnData.put("msg", "用户名或密码为空!");
            returnData.put("data", "");
        }
        User user = userService.getByUsername(username);
        if(user==null){
            returnData.put("status", 1);
            returnData.put("msg", "用户名不存在!");
            returnData.put("data", "");
        } else if (user.getPassword().equals(password)) {
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
        user.setUserType(1);
        user.setIsDelete(0);
        if (userService.getByUsername(user.getUsername())!=null) {
            userService.save(user);
            return DataEcho.NoDataSuccess();
        }
        returnData.put("status", 0);
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
