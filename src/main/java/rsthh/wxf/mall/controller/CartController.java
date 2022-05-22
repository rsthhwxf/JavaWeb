package rsthh.wxf.mall.controller;


import rsthh.wxf.mall.po.*;
import rsthh.wxf.mall.service.*;
import rsthh.wxf.mall.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    @GetMapping("/myCart")
    public String myCart(HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = ThreadLocalUtil.getUser().getId();
        List<Cart> cartList = cartService.getCarts(userID);
        Map data = new HashMap<>();
        data.put("cartList", cartList);
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", data);
        return JsonUtil.toJson(returnData);
    }


    @PostMapping("/add")
    public String addItem(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = ThreadLocalUtil.getUser().getId();
        Integer itemID = (Integer) map.get("itemID");
        Item item = null;
        item = itemService.getById(itemID);
        if (item == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        Cart cart = new Cart();
        cart = cartService.getCartByItem(item.getId(), userID);
        if (cart.getName()!="") {
            cartService.updateCartNum(itemID, cart.getNum() + 1);
        } else {
            cart.setNum(1);
            cart.setPrice(item.getPrice());
            cart.setName(item.getItemName());
            cart.setItemId(itemID);
            cart.setUserId(userID);
            cart.setManageId(item.getManageId());
            cart.setImage(item.getImage());
            cartService.save(cart);
        }

        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestBody Map map, HttpServletRequest httpServletRequest) throws Exception {
        Map returnData = new HashMap<>();
        Integer userID = ThreadLocalUtil.getUser().getId();
        Integer itemID = (Integer) map.get("itemID");
        Item item = itemService.getById(itemID);
        if (item == null) {
            returnData.put("status", 1);
            returnData.put("msg", "商品不存在!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        Cart cart = cartService.getCartByItem(item.getId(), userID);
        Integer num = cart.getNum();
        if (num == 1) {
            cartService.removeById(cart.getId());
        }
        else{
            String total = String.valueOf(cart.getPrice() * (num - 1));
            cartService.updateCartNum(itemID, num - 1);
        }
        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/buy")
    public String exAdd(@RequestBody List<Integer> list, HttpServletRequest request) throws Exception {
        Map returnData = new HashMap();
        User user = ThreadLocalUtil.getUser();
        if (user.getAddress()=="") {
            returnData.put("status", "1");
            returnData.put("msg", "请填写收货地址!");
            returnData.put("data", "");
            return JsonUtil.toJson(returnData);
        }
        for (Integer cartID : list) {
            Cart cart = cartService.getById(cartID);
            if (cart == null) continue;
            Integer itemID = cart.getItemId();
            Item item = itemService.getById(itemID);
            Orders order = new Orders();
            order.setItemId(itemID);
            order.setUserId(user.getId());
            order.setManageId(item.getManageId());
            order.setPrice(cart.getPrice());
            order.setNum(cart.getNum());
            order.setStatus(1);
            order.setItem_name(cart.getName());
            order.setImage(item.getImage());
            orderService.save(order);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCode(UUIDUtils.create());
            orderDetail.setImage(item.getImage());
            orderDetail.setNum(cart.getNum());
            orderDetail.setManageId(cart.getManageId());
            orderDetail.setAddress(user.getAddress());
            orderDetail.setItemName(item.getItemName());
            orderDetail.setPhone(user.getPhone());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setItemId(item.getId());
            orderDetail.setUserId(item.getId());
            orderDetailService.save(orderDetail);
        }
        return DataEcho.NoDataSuccess();
    }


}
