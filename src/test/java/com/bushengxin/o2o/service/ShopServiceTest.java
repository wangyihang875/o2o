package com.bushengxin.o2o.service;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.Area;
import com.bushengxin.o2o.entity.PersonInfo;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.entity.ShopCategory;
import com.bushengxin.o2o.enums.ShopStateEnum;
import com.bushengxin.o2o.util.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() throws Exception {
        Shop shop = new Shop();

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory sc = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1L);
        sc.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(sc);
        shop.setShopName("mytest2");
        shop.setShopDesc("mytest2");
        shop.setShopAddr("testaddr2");
        shop.setPhone("13888888888");
        shop.setShopImg("test2");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("/Users/wangyihang/Pictures/com.bushengxin.o2o/stars.jpg");
        FileItem fileItem = FileUtil.createFileItem(shopImg, "shopImg");
        CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(fileItem);
        ShopExecution se = shopService.addShop(shop, commonsMultipartFile);
        assertEquals("mytest2", se.getShop().getShopName());
    }


//	@Test
//	public void testGetByEmployeeId() throws Exception {
//		long employeeId = 1;
//		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
//		List<Shop> shopList = shopExecution.getShopList();
//		for (Shop shop : shopList) {
//			System.out.println(shop);
//		}
//	}

	@Test
	public void testModifyShop() throws Exception {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory sc = new ShopCategory();
        owner.setUserId(2L);
        area.setAreaId(2L);
        sc.setShopCategoryId(2L);
        shop.setShopId(9L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(sc);
        shop.setShopName("小果园");
        shop.setShopDesc("请你来喝");
        shop.setShopAddr("23号");
        shop.setPhone("13909090909");
        shop.setShopImg("test2");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("斤斤计较");
        File shopImg = new File("/Users/wangyihang/Pictures/com.bushengxin.o2o/tiger.jpg");
        FileItem fileItem = FileUtil.createFileItem(shopImg, "shopImg");
        CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(fileItem);
        ShopExecution se = shopService.modifyShop(shop, commonsMultipartFile);
        assertEquals("小果园", se.getShop().getShopName());
	}

	@Test
	public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopExecution se = shopService.getShopList(shopCondition,0,100);
        System.out.println("总条数："+se.getShopList().size());
        PersonInfo owner = new PersonInfo();
        owner.setUserId(2L);
        shopCondition.setOwner(owner);
        se = shopService.getShopList(shopCondition,0,100);
        System.out.println("获取到的条数："+se.getShopList().size());
    }
}
