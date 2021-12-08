package com.liasplf.ncumall.controller.user;

import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.service.CommentService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
