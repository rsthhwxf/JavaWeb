package com.liasplf.ncumall.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.service.CommentService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/item")
public class UserItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @RequestMapping("/viewItem")
    public String viewItem(Integer id, Model model){
        Item item = itemService.getById(id);
        List<Comment> comments = commentService.getItemComments(id);
        for (Comment comment : comments) {
            comment.setUser(userService.getById(comment.getUserId()));
        }
        item.setComments(comments);
        model.addAttribute("item",item);
        return "front/viewItem";
    }

    @RequestMapping("/itemList")
    public String itemList(Integer categoryId2,Integer condition, Model model, HttpServletRequest request,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        List<Item> itemList = itemService.getItemsByCat2(categoryId2,condition);
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
            System.out.println("分页数据："+itemList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Item> pageInfo = new PageInfo<Item>(itemList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",itemList);
            model.addAttribute("cate2",categoryId2);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }

        return "front/items";
    }

}
