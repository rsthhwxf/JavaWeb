package com.liasplf.ncumall.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/login")
public class LoginController {
    @Autowired
    private ManageService manageService;

    @RequestMapping("login")
    public String login(){
        return "login/mLogin";
    }

    @RequestMapping("toLogin")
    public String toLogin(Model model , String userName, String passWord, HttpServletRequest request){
        Manage manage = manageService.getOne(new QueryWrapper<Manage>().eq("username", userName));
        if(manage != null && manage.getPassWord().equals(passWord)){
            model.addAttribute("manage",manage);
            request.getSession().setAttribute("manage",manage);
            request.getSession().setAttribute("id",manage.getId());
            return "login/mIndex";
        }
        model.addAttribute("info","用户名或密码错误");
        return "login/mLogin";
    }

    @RequestMapping("mExit")
    public String mExit(HttpServletRequest request){
        request.getSession().removeAttribute("manage");
        return "login/mLogin";
    }
}
