package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.po.ItemDetail;
import rsthh.wxf.mall.service.CommentService;
import rsthh.wxf.mall.service.ItemDetailService;
import rsthh.wxf.mall.service.ItemService;
import rsthh.wxf.mall.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ItemDetailService itemDetailService;

    @PostMapping("/detail")
    public String itemInfo(@RequestBody Map map){
        Map returnData = new HashMap();
        Integer itemID = (Integer) map.get("itemID");
        ItemDetail itemDetail = itemDetailService.getById(itemID);
        if (itemDetail == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        itemDetail.setComments(commentService.getItemComments(itemID));
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemDetail);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/search")//有待es
    public String itemSearch(@RequestBody Map map){
        Map returnData = new HashMap();
        String keyword = (String) map.get("keyword");
        List<Item> itemList = itemService.searchItem(keyword);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }

    @GetMapping("/list")//有待分页
    public String itemList(){
        List<Item> itemList = itemService.getItemList();
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }
}
