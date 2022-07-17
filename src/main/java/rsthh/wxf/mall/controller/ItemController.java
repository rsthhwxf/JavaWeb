package rsthh.wxf.mall.controller;


import com.github.pagehelper.PageInfo;
import rsthh.wxf.mall.po.*;
import rsthh.wxf.mall.service.*;
import rsthh.wxf.mall.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rsthh.wxf.mall.vo.SearchParam;
import rsthh.wxf.mall.vo.SearchResult;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;


    @PostMapping("/detail")
    public String itemInfo(@RequestBody Map map){
        Map returnData = new HashMap();
        Integer itemID = (Integer) map.get("itemID");
        int pageNum=(int)map.get("CommentPageNum");
        int pageSize=(int)map.get("CommentPageSize");
        ItemDetail itemDetail = itemDetailService.getById(itemID);
        if (itemDetail == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        itemDetail.setComments(commentService.getItemComments(itemID,pageNum,pageSize));
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemDetail);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/search")//有待es
    public String itemSearch(@RequestBody SearchParam searchParam){
        SearchResult itemList = itemService.searchItem(searchParam);
        return DataEcho.NoDataSuccess();
    }

    @GetMapping("/list")//有待分页
    public String itemList(@RequestBody Map map){
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        List<Item> itemList = itemService.getItemList(pageNum,pageSize);
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/buy")
    public String buyOne(@RequestBody Map map,HttpServletRequest request) throws Exception {
        Map returnData = new HashMap();
        User user = ThreadLocalUtil.getUser();
        if (user.getAddress()=="") {
            returnData.put("status", "1");
            returnData.put("msg", "请填写收货地址!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        Integer itemID = (Integer) map.get("itemID");
        Item item = itemService.getById(itemID);
        Orders order = new Orders();
        order.setItemId(itemID);
        order.setUserId(user.getId());
        order.setManageId(item.getManageId());
        order.setPrice(item.getPrice());
        order.setNum(1);
        order.setStatus(1);
        order.setItem_name(item.getItemName());
        order.setImage(item.getImage());
        orderService.save(order);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCode(UUIDUtils.create());
        orderDetail.setImage(item.getImage());
        orderDetail.setNum(1);
        orderDetail.setManageId(item.getManageId());
        orderDetail.setAddress(user.getAddress());
        orderDetail.setItemName(item.getItemName());
        orderDetail.setPhone(user.getPhone());
        orderDetail.setPrice(item.getPrice());
        orderDetail.setItemId(item.getId());
        orderDetail.setUserId(item.getId());
        orderDetailService.save(orderDetail);

        return DataEcho.NoDataSuccess();
    }
}
