package com.bushengxin.o2o.web.frontend;

import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.Area;
import com.bushengxin.o2o.entity.HeadLine;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.entity.ShopCategory;
import com.bushengxin.o2o.enums.HeadLineStateEnum;
import com.bushengxin.o2o.enums.ShopCategoryStateEnum;
import com.bushengxin.o2o.service.AreaService;
import com.bushengxin.o2o.service.HeadLineService;
import com.bushengxin.o2o.service.ShopCategoryService;
import com.bushengxin.o2o.service.ShopService;
import com.bushengxin.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getshoplist(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            long areaId = HttpServletRequestUtil.getLong(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition4Search(shopCategoryId, parentId, areaId, shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }

    @RequestMapping(value = "/getshoplistpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getshoplistpageinfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {//如果有parentId，则取出一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {//如果没有parentId，则取出一级ShopCategory列表
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    private Shop compactShopCondition4Search(long shopCategoryId, long parentId, long areaId, String shopName) {
        Shop shopCondition = new Shop();
        //写到这里确实发现tb_shop表写得有点问题，应该添加parent_category_id字段方便查询
		//所以有多级分类查询的时候，要把各级分类id的字段都添加在主体表中
        if (parentId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			ShopCategory shopParentCategory = new ShopCategory();
			shopParentCategory.setShopCategoryId(parentId);
			shopCategory.setParent(shopParentCategory);
            shopCondition.setShopCategory(shopCategory);
        }
        if (shopCategoryId != -1L) { //当有shopCategoryId，则这一个shopCategory会覆盖上一个shopCategory，
        							// 所以只有shopCategoryId生效而parentId不生效
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
