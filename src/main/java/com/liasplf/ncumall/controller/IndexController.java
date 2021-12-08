package com.liasplf.ncumall.controller;

import com.liasplf.ncumall.po.CategoryDto;
import com.liasplf.ncumall.po.Item;
import com.liasplf.ncumall.po.ItemCategory;
import com.liasplf.ncumall.service.ItemCategoryService;
import com.liasplf.ncumall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping("/uIndex")
    public String index(Model model, Item item, HttpServletRequest request){
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
            model.addAttribute("lbs",list);
        }
        //折扣商品
        List<Item> zks = itemService.getTopZk();
        model.addAttribute("zks",zks);

        //热销商品
        List<Item> rxs = itemService.getTopRx();
        model.addAttribute("rxs",rxs);
        return "index/uIndex";
    }



    @RequestMapping("test")
    public String test(){
        return "common/test";
    }
}
