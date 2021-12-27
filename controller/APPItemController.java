package com.liasplf.ncumall.controller;


import com.liasplf.ncumall.po.Comment;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.service.AppItemService;
import com.liasplf.ncumall.service.CommentService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.service.UserService;
import com.liasplf.ncumall.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/item")
public class APPItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppItemService appItemService;

    @PostMapping("/details")
    public String itemInfo(@RequestBody Map map){
        Map returnData = new HashMap();
        Integer itemID = (Integer) map.get("itemID");
        Item item = itemService.getById(itemID);
        if (item == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        List<Comment> comments = commentService.getItemComments(itemID);
        for (Comment comment : comments) {
            comment.setUser(userService.getById(comment.getUserId()));
        }
        item.setComments(comments);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", item);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/search")
    public String itemSearch(@RequestBody Map map){
        Map returnData = new HashMap();
        String keyword = (String) map.get("keyword");
        List<Item> itemList = appItemService.searchItem(keyword);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }

    @GetMapping("/itemList")
    public String itemList(){
        List<Item> itemList = appItemService.getItemList();
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }
}
