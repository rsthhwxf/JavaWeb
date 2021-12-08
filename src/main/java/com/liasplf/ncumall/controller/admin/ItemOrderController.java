package com.liasplf.ncumall.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liasplf.ncumall.po.ItemOrder;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/itemOrder")
public class ItemOrderController {
    @Autowired
    private ItemOrderService itemOrderService;

    @RequestMapping("/listItemOrder")
    public String listItemOrder(Model model, HttpServletRequest request,String code,
                                @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        Manage manage = (Manage) request.getSession().getAttribute("manage");


        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        PageHelper.startPage(pageNum,pageSize);
        try {
            List<ItemOrder> itemOrderList;
            if(code!=null){
                itemOrderList = itemOrderService.searchItemOrder(code,manage.getId());
            }else{
                itemOrderList = itemOrderService.searchItemOrder(null,manage.getId());
            }
            System.out.println("分页数据："+itemOrderList);
            PageInfo<ItemOrder> pageInfo = new PageInfo<ItemOrder>(itemOrderList,pageSize);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",itemOrderList);
        }finally {
            PageHelper.clearPage();
        }
        return "itemOrder/itemOrder";
    }

    @RequestMapping("/send")
    public String send(Integer id){
        ItemOrder order =itemOrderService.getById(id);
        order.setStatus(2);
        itemOrderService.updateById(order);
        return "redirect:/admin/itemOrder/listItemOrder";
    }

}
