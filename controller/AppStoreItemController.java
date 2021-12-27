package com.liasplf.ncumall.controller;


import com.aliyun.oss.OSS;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.*;
import com.liasplf.ncumall.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/store/item")
public class AppStoreItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private OSS ossClient;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ManageService manageService;

    @GetMapping("/itemList")
    public String listItemOrder(HttpServletRequest request) throws Exception {
        Integer manageID = TokenUtil.getIDByRequest(request);
        Manage manage = manageService.getById(manageID);
        if (manage == null) {
            DataEcho.NoAuthority();
        }
        List<Item> itemList = itemService.getItemByManageID(manageID);
        Map returnData = new HashMap();
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemList);
        return JsonUtil.toJson(returnData);
    }

    @PostMapping("/search")
    public String searchOrder(HttpServletRequest httpServletRequest, @RequestBody Map map) throws Exception {
        Map returnData = new HashMap();
        Manage manage = manageService.getById(TokenUtil.getIDByRequest(httpServletRequest));
        if (manage == null) {
            DataEcho.NoAuthority();
        }
        String keyword = (String) map.get("keyword");
        List<Item> itemOrderList = itemService.searchItem(keyword,manage.getId());
        returnData.put("status", 0);
        returnData.put("msg", "成功!");
        returnData.put("data", itemOrderList);
        return JsonUtil.toJson(returnData);
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        Map returnData = new HashMap();
        Item item = itemService.getById(id);
        itemService.updateById(item);
        DataEcho.NoDataSuccess();
        return JsonUtil.toJson(returnData);
    }

    private void itemUpload(Item item, @RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        if(files.length>0) {
            for (int s = 0; s < files.length; s++) {
                String n = UUIDUtils.create();
                String path;
                String fileName = files[s].getOriginalFilename();
                if(fileName==null || fileName.length()==0)continue;

                if(!fileName.contains(Consts.OSS_PRE_PATH)){
                    path = Consts.OSS_PRE_PATH + n + fileName;
                    System.out.println(path);
                    InputStream inputStream = files[s].getInputStream();
                    ossClient.putObject("ncumall", n+files[s].getOriginalFilename(), inputStream);

                    switch (s){
                        case 0:
                            item.setUrl1(path);
                            break;
                        case 1:
                            item.setUrl2(path);
                            break;
                        case 2:
                            item.setUrl3(path);
                            break;
                        case 3:
                            item.setUrl4(path);
                            break;
                        case 4:
                            item.setUrl5(path);
                            break;
                    }
                }

            }
        }
        ItemCategory byId = itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
    }

}
