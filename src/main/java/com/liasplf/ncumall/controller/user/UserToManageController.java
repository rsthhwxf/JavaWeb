package com.liasplf.ncumall.controller.user;

import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/manage")
public class UserToManageController {
    @Autowired
    private ManageService manageService;

    @RequestMapping("/becomeManage")
    public String becomeManage(){
        return "user/becomeManage";
    }

    @RequestMapping("/regisManage")
    public String regisManage(Manage manage, Model model){
        Manage manage1 = manageService.searchByName(manage.getUserName());
        if (manage1==null){
            manage.setCreateTime(new Date());
            manageService.save(manage);
            return "redirect:/index/uIndex";
        }else{
            model.addAttribute("info","当前商家信息已存在");
            return "user/becomeManage";
        }
    }
}
