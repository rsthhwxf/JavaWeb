package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.Comment;
import rsthh.wxf.mall.service.CommentService;
import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.DataEcho;
import rsthh.wxf.mall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Map returnData = new HashMap();
        Integer itemID = (Integer) map.get("itemID");
        String content = (String) map.get("content");
        Integer userID = TokenUtil.getIDByRequest(httpServletRequest);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setItemId(itemID);
        comment.setUserId(userID);
        comment.setUsername(userService.getById(userID).getUsername());
        commentService.save(comment);
        return  DataEcho.NoDataSuccess();
    }
}
