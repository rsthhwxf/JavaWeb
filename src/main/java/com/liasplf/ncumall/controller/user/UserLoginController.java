package com.liasplf.ncumall.controller.user;

import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.UserService;
import com.liasplf.ncumall.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserLoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/uLogin")
    public String uLogin(){
        return "user/userLogin";
    }

    @RequestMapping("/utoLogin")
    public String utoLogin(String userName, String passWord, HttpServletRequest request, Model model){
        User user = userService.getByUserName(userName);
        if(user!=null && user.getPassWord().equals(passWord)){
            request.getSession().setAttribute("role",2);
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute(Consts.USERNAME,user.getUserName());
            request.getSession().setAttribute(Consts.USERID,user.getId());
            return "redirect:/index/uIndex";
        }
        model.addAttribute("info","用户名或密码错误");
        return "user/userLogin";
    }

    @RequestMapping("/uRegis")
    public String regis(){
        return "user/regis";
    }

    @RequestMapping("/toRegis")
    public String toRegis(User user,Model model){
        if(userService.checkUser(user.getUserName())){
            userService.save(user);
            return "redirect:/user/uLogin";
        }
        model.addAttribute("info","账户已存在！");
        return "user/regis";
    }

    @RequestMapping("/uExit")
    public String uExit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index/uIndex";
    }


}
