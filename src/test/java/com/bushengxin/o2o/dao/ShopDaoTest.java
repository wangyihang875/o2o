package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.entity.Area;
import com.bushengxin.o2o.entity.PersonInfo;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.entity.ShopCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testInsertShop() throws Exception {
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
		shop.setShopName("mytest3");
		shop.setShopDesc("mytest3");
		shop.setShopAddr("testaddr3");
		shop.setPhone("13555555555");
		shop.setShopImg("test3");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("嘿嘿嘿");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}
