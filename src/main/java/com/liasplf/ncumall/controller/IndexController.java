package com.liasplf.ncumall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liasplf.ncumall.po.CategoryDto;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.service.ItemCategoryService;
import com.liasplf.ncumall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("index")
public class IndexController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/uIndex")
    public String index(Model model, Item item, HttpServletRequest request){
        String lbsStr = redisTemplate.opsForValue().get("lbsJson");
        String zksStr = redisTemplate.opsForValue().get("zksJson");
        String rxsStr = redisTemplate.opsForValue().get("rxsJson");
        //分类数据
        if(StringUtils.isEmpty(lbsStr)){
            //数据库查询
            System.out.println("mysql查询数据");
            synchronized (this){
                String lbsStr1 = redisTemplate.opsForValue().get("lbsJson");
                if (StringUtils.isEmpty(lbsStr1)){
                    System.out.println("查mysql");
                    List<ItemCategory> categoryList1 = itemCategoryService.getAll();
                    List<CategoryDto> list = new ArrayList<>();
                    if(!CollectionUtils.isEmpty(categoryList1)){
                        for(ItemCategory ic:categoryList1){
                            CategoryDto dto = new CategoryDto();
                            dto.setFather(ic);
                            //查询二级类目
                            List<ItemCategory> childrens = itemCategoryService.getAll2(ic.getId());
                            dto.setChildrens(childrens);
                            list.add(dto);
                        }
                        model.addAttribute("lbsJson",list);
                        String lbsJson = JSON.toJSONString(list);
                        redisTemplate.opsForValue().set("lbsJson",lbsJson,1, TimeUnit.HOURS);
                    }
                }else{
                    System.out.println("redis查询数据");
                    List<CategoryDto> list = JSON.parseObject(lbsStr1,new TypeReference<List<CategoryDto>>(){});
                    model.addAttribute("lbsJson",list);
                }
            }
        }else{
            System.out.println("redis查询数据");
            List<CategoryDto> list = JSON.parseObject(lbsStr,new TypeReference<List<CategoryDto>>(){});
            model.addAttribute("lbsJson",list);
        }
        //折扣数据
        if (StringUtils.isEmpty(zksStr)){
            synchronized (this){
                String zksStr1 = redisTemplate.opsForValue().get("zksJson");
                if (StringUtils.isEmpty(zksStr1)){
                    System.out.println("查mysql");
                    //折扣商品
                    List<Item> zks = itemService.getTopZk();
                    model.addAttribute("zks",zks);
                    String zksJson = JSON.toJSONString(zks);
                    redisTemplate.opsForValue().set("zksJson",zksJson,1, TimeUnit.HOURS);
                }else{
                    System.out.println("redis查询数据");
                    List<Item> list = JSON.parseObject(zksStr1,new TypeReference<List<Item>>(){});
                    model.addAttribute("zks",list);
                }
            }
        }else{
            System.out.println("redis查询数据");
            List<Item> list = JSON.parseObject(zksStr,new TypeReference<List<Item>>(){});
            model.addAttribute("zks",list);
        }

        //热销数据
        if (StringUtils.isEmpty(rxsStr)){
            synchronized (this){
                String rxsStr1 = redisTemplate.opsForValue().get("rxsJson");
                if (StringUtils.isEmpty(rxsStr1)){
                    System.out.println("查mysql");
                    //热销商品
                    List<Item> rxs = itemService.getTopRx();
                    model.addAttribute("rxs",rxs);
                    String rxsJson = JSON.toJSONString(rxs);
                    redisTemplate.opsForValue().set("rxsJson",rxsJson,1, TimeUnit.HOURS);
                }else{
                    System.out.println("redis查询数据");
                    List<Item> list = JSON.parseObject(rxsStr1,new TypeReference<List<Item>>(){});
                    model.addAttribute("rxs",list);
                }
            }
        }else{
            System.out.println("redis查询数据");
            List<Item> list = JSON.parseObject(rxsStr,new TypeReference<List<Item>>(){});
            model.addAttribute("rxs",list);
        }

        return "index/uIndex";
    }



    @RequestMapping("test")
    public String test(){
        return "common/test";
    }
}
