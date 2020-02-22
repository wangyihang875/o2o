package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.entity.ProductCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)//控制测试方式执行顺序 如将方法命名为testA，testB，testC则将会按顺序执行,形成一个测试闭环
public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testBQueryShopCategory() throws Exception {

		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(13L);
		ObjectMapper mapper = new ObjectMapper();
		String listJson = mapper.writeValueAsString(productCategoryList);
		System.out.println("productCategoryList:"+listJson);
	}

	@Test
	public void testABatchInsertProductCategory(){
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryName("垃圾套餐");
		pc.setShopId(13L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("便宜套餐");
		pc2.setShopId(13L);
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(pc);
		list.add(pc2);
		int effectedNum = productCategoryDao.batchInsertProductCategory(list);
		assertEquals(2,effectedNum);
	}

	@Test
	public void testCDeleteProductCategory(){
		long shopId = 13L;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(shopId);
		for(ProductCategory pc : productCategoryList){
			if("垃圾套餐".equals(pc.getProductCategoryName())||"便宜套餐".equals(pc.getProductCategoryName())){
				productCategoryDao.deleteProductCategory(pc.getProductCategoryId(),shopId);
			}
		}
	}

}
