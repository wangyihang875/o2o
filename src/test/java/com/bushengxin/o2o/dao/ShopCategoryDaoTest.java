package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.entity.ShopCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void testQueryShopCategory() throws Exception {
		ShopCategory sc = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(sc);
		assertEquals(3, shopCategoryList.size());

		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(3L);
		testCategory.setParent(parentCategory);
		shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);
		assertEquals(3, shopCategoryList.size());

		List<ShopCategory> shopCategoryList2 = shopCategoryDao.queryShopCategory(null);
		assertEquals(4, shopCategoryList2.size());

//		sc.setParentId(1L);
//		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
//		assertEquals(1, shopCategoryList.size());
//		sc.setParentId(null);
//		sc.setShopCategoryId(0L);
//		shopCategoryList = shopCategoryDao.queryShopCategory(sc);
//		assertEquals(2, shopCategoryList.size());

	}

}
