package com.liasplf.ncumall.controller.user;

import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemOrder;
import com.liasplf.ncumall.po.OrderDetail;
import com.liasplf.ncumall.po.User;
import com.liasplf.ncumall.service.ItemOrderService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.OrderDetailService;
import com.liasplf.ncumall.service.UserService;
import com.liasplf.ncumall.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ItemService itemService;

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

    @RequestMapping("/self")
    public String self(HttpServletRequest request,Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "user/self";
    }

    @RequestMapping("/update")
    public String update(User user){
        userService.updateById(user);
        return "redirect:/user/self";
    }

    @RequestMapping("/myOrder")
    public String myOrder(HttpServletRequest request,Model model){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/user/uLogin";
        }

        List<ItemOrder> list = itemOrderService.getItemOrderByUserId(user.getId());
        List<ItemOrder> waitSent = new ArrayList<>();
        List<ItemOrder> cancel = new ArrayList<>();
        List<ItemOrder> waitGet = new ArrayList<>();
        List<ItemOrder> getItem = new ArrayList<>();

        for (ItemOrder itemOrder : list) {
            String code = itemOrder.getCode();
            System.out.println(code);
            OrderDetail orderDetail = orderDetailService.getByOrderCode(code);
            Item item = itemService.getById(itemOrder.getItemId());
            orderDetail.setItem(item);

            itemOrder.getDetails().add(orderDetail);

            switch (itemOrder.getStatus()){
                case 0:
                    waitSent.add(itemOrder);
                    break;
                case 2:
                    cancel.add(itemOrder);
                    break;
                case 3:
                    waitGet.add(itemOrder);
                    break;
                case 4:
                    getItem.add(itemOrder);
                    break;
            }
        }

        model.addAttribute("all",list);
        model.addAttribute("waitSent",waitSent);
        model.addAttribute("cancel",cancel);
        model.addAttribute("waitGet",waitGet);
        model.addAttribute("getItem",getItem);

        return "user/myOrder";
    }
}
