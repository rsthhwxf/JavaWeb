package rsthh.wxf.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.service.EsService;
import rsthh.wxf.mall.service.ItemService;
import rsthh.wxf.mall.service.impl.ItemServiceImpl;
import rsthh.wxf.mall.utils.DataEcho;
import rsthh.wxf.mall.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EsService esService;

    @PostMapping("/up")
    public String up(@RequestBody Item item) throws IOException {
        itemService.save(item);
        esService.agreement(item);
        return DataEcho.NoDataSuccess();
    }

    @PostMapping("/down")
    public String down(@RequestBody Map map) throws IOException {
        Integer itemID = (Integer) map.get("id");
        itemService.removeById(itemID);
        esService.agreement2(itemID.toString());
        return DataEcho.NoDataSuccess();
    }
}
