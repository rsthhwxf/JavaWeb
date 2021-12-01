package com.liasplf.ncumall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController {
    @Autowired
    private ItemCategoryService itemCategoryService;


    @RequestMapping("/listCategory1")
    public String listCategory1(Model model,
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
            List<ItemCategory> userList = itemCategoryService.getAll();
            System.out.println("分页数据："+userList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ItemCategory> pageInfo = new PageInfo<ItemCategory>(userList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",userList);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        //5.设置返回的jsp/html等前端页面
        return "itemCategory/itemCategory";
    }

    @RequestMapping("/add")
    public String add(){
        return "itemCategory/add";
    }

    @RequestMapping("/catAdd")
    public String catAdd(String name){
        ItemCategory category = new ItemCategory();
        category.setIsDelete(0);
        category.setPid(0);
        category.setName(name);
        itemCategoryService.save(category);
        return "redirect:/itemCategory/listCategory1";
    }

    @RequestMapping("/update1")
    public String update1(Integer id,Model model){
        ItemCategory category = itemCategoryService.getById(id);
        model.addAttribute("cate",category);
        return "itemCategory/update";
    }

    @RequestMapping("/catUpdate")
    public String catUpdate(ItemCategory category){

        itemCategoryService.updateById(category);
        return "redirect:/itemCategory/listCategory1";
    }
    @RequestMapping("/delete1")
    public String update1(Integer id){
        itemCategoryService.removeById(id);
        return "redirect:/itemCategory/listCategory1";
    }

    @RequestMapping("/listCategory2")
    public String listCategory2( Integer pid,
                                Model model,
                                @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                                @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

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
            List<ItemCategory> userList = itemCategoryService.getAll2(pid);
            System.out.println("分页数据："+userList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ItemCategory> pageInfo = new PageInfo<ItemCategory>(userList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",userList);
            model.addAttribute("pid",pid);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        //5.设置返回的jsp/html等前端页面
        return "itemCategory/itemCategory2";
    }

    @RequestMapping("/add2")
    public String add2(Integer pid,Model model){
        model.addAttribute("pid",pid);
        return "itemCategory/add2";
    }
    @RequestMapping("/catAdd2")
    public String catAdd2(Integer pid,String name){
        ItemCategory category = new ItemCategory();
        category.setIsDelete(0);
        category.setPid(pid);
        category.setName(name);
        itemCategoryService.save(category);
        return "redirect:/itemCategory/listCategory1";
    }
    @RequestMapping("/update2")
    public String update2(Integer id,Model model){
        ItemCategory category = itemCategoryService.getById(id);
        model.addAttribute("cate",category);
        return "itemCategory/update2";
    }
    @RequestMapping("/catUpdate2")
    public String catUpdate2(ItemCategory category){

        itemCategoryService.updateById(category);
        return "redirect:/itemCategory/listCategory1";
    }
}
