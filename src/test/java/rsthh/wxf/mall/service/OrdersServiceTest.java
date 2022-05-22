package rsthh.wxf.mall.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.po.Orders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Test
    public void getOrderByUserId(){
        orderService.getOrderByUserId(1,2,2);
    }

    @Test
    public void insert(){
        Integer itemID = 1;
        System.out.println(itemService);
        Item item = itemService.getById(1);
        Integer userId = 1;
        Orders order = new Orders();
        order.setItemId(itemID);
        order.setUserId(userId);
        order.setManageId(item.getManageId());
        order.setPrice(item.getPrice());
        order.setNum(1);
        order.setStatus(1);
        order.setItem_name(item.getItemName());
        order.setImage(item.getImage());
        orderService.save(order);
    }
}
