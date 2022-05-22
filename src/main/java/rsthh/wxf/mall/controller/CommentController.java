package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.Comment;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.service.CommentService;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.DataEcho;
import rsthh.wxf.mall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsthh.wxf.mall.utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addComment(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Integer itemID = (Integer) map.get("itemID");
        String content = (String) map.get("content");
        User user = ThreadLocalUtil.getUser();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setItemId(itemID);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        commentService.save(comment);
        return  DataEcho.NoDataSuccess();
    }
}
