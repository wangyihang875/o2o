package com.bushengxin.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {

    @RequestMapping(value = "/shopedit")
    public String shopEdit(){
        return "shop/shopedit";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage")
    public String shopManage(){
        return "shop/shopmanage";
    }

    @RequestMapping(value = "/productcategorymanage")
    public String productCategoryManage(){
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/productmanage")
    private String productManage() {
        return "shop/productmanage";
    }

    @RequestMapping(value = "/productedit")
    private String productEdit() {
        return "shop/productedit";
    }
}
