package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.service.CommentService;
import com.liasplf.ncumall.utils.DataEcho;
import com.liasplf.ncumall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/comment")
public class AppCommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap();
        Integer itemID = (Integer) map.get("itemID");
        String content = (String) map.get("content");
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setItemId(itemID);
        comment.setAddTime(new Date());
        comment.setUserId(userID);
        commentService.save(comment);
        return  DataEcho.NoDataSuccess();
    }
}
