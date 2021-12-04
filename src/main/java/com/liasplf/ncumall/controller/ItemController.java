package com.liasplf.ncumall.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.po.Manage;
import com.liasplf.ncumall.service.ItemCategoryService;
import com.liasplf.ncumall.service.ItemService;
import com.liasplf.ncumall.utils.Consts;
import com.liasplf.ncumall.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private OSS ossClient;

    @RequestMapping("/listItem")
    public String listItem(Model model, HttpServletRequest request,String itemName,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){

        Manage manage = (Manage)request.getSession().getAttribute("manage");
        Integer status = manage.getStatus();


        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Item> itemList;
            if(itemName!=null){
                itemList = itemService.searchItem(itemName,status,manage.getId());
            }else{
                itemList = itemService.getAll(status,manage.getId());
            }
            System.out.println("分页数据："+itemList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Item> pageInfo = new PageInfo<Item>(itemList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("list",itemList);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }

        return "item/item";
    }

    @RequestMapping("/add")
    public String add(Model model){

        List<ItemCategory> category2 = itemCategoryService.getAllCategory2();
        model.addAttribute("types",category2);
        return "item/add";
    }

    @RequestMapping("/itemAdd")
    public String itemAdd(Item item, @RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        itemUpload(item, files, request);
        item.setGmNum(0);
        item.setIsDelete(0);
        item.setScNum(0);
        Manage manage = (Manage) request.getSession().getAttribute("manage");
        item.setManageId(manage.getId());
        itemService.save(item);
        return "redirect:/item/listItem";
    }

    @RequestMapping("/update")
    public String update(Integer id,Model model){
        Item item = itemService.getById(id);
        List<ItemCategory> listBySqlReturnEntity = itemCategoryService.getAllCategory2();
        model.addAttribute("types",listBySqlReturnEntity);
        model.addAttribute("item",item);
        return "item/update";
    }

    @RequestMapping("/itemUpdate")
    public String itemUpdate(RedirectAttributes attributes, Item item, @RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        itemUpload(item, files, request);
        itemService.updateById(item);
        attributes.addFlashAttribute("manage",request.getSession().getAttribute("manage"));
        return "redirect:/item/listItem";
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        Item item = itemService.getById(id);
        item.setIsDelete(1);
        itemService.updateById(item);
        return "redirect:/item/listItem";
    }

    private void itemUpload(Item item, @RequestParam("file") MultipartFile [] files, HttpServletRequest request) throws IOException {
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

//                if (s == 0) {
//                    item.setUrl1(Consts.OSS_PRE_PATH + n + files[s].getOriginalFilename());
//                }
//                if (s == 1) {
//                    item.setUrl2(Consts.OSS_PRE_PATH + n + files[s].getOriginalFilename());
//                }
//                if (s == 2) {
//                    item.setUrl3(Consts.OSS_PRE_PATH + n + files[s].getOriginalFilename());
//                }
//                if (s == 3) {
//                    item.setUrl4(Consts.OSS_PRE_PATH + n + files[s].getOriginalFilename());
//                }
//                if (s == 4) {
//                    item.setUrl5(Consts.OSS_PRE_PATH + n + files[s].getOriginalFilename());
//                }
            }
        }
        ItemCategory byId = itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
    }
}
