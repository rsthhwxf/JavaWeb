package com.liasplf.ncumall.controller.user;

import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.service.CommentService;
import com.liasplf.ncumall.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/comment")
public class UserCommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/add")
    public String add(Integer itemId, String content, HttpServletRequest request){
        Comment comment = new Comment();
        Integer id = (Integer) request.getSession().getAttribute(Consts.USERID);
        comment.setUserId(id);
        comment.setItemId(itemId);
        comment.setContent(content);
        comment.setAddTime(new Date());
        commentService.save(comment);
        return "redirect:/user/myOrder";
    }
}
