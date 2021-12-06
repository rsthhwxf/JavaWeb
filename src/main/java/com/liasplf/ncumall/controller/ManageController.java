package com.liasplf.ncumall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/manage")
public class ManageController {
    @Autowired
    private ManageService manageService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/listManage")
    public String findBySql(Model model, String shopName,
                            @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                            @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Manage> manageList;
            manageList = manageService.getManagers(shopName);

            System.out.println("分页数据："+manageList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Manage> pageInfo = new PageInfo<Manage>(manageList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",manageList);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "manage/manage";
    }

    @RequestMapping("/update")
    public String update(Integer id,Model model){
        Manage manage = manageService.getById(id);
        model.addAttribute("manage",manage);
        return "manage/update";
    }

    @RequestMapping("/manageUpdate")
    public String manageUpdate(Manage manage){
        manageService.updateById(manage);
        return "redirect:/manage/listManage";
    }

    @RequestMapping("/lookShop")
    public String lookShop(Integer id,Model model, HttpServletRequest request, String itemName,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        List<Item> list = itemService.getItemByManageId(id);
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            System.out.println("分页数据："+list);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Item> pageInfo = new PageInfo<Item>(list,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",list);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }

        return "manage/lookShop";
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        Manage manage = manageService.getById(id);
        manage.setIsDelete(1);
        manageService.updateById(manage);
        return "redirect:/manage/listManage";
    }




}
