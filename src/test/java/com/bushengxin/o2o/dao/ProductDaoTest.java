package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.entity.Product;
import com.bushengxin.o2o.entity.ProductCategory;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.util.JSONUtil;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct() throws Exception {
        Shop shop1 = new Shop();
        shop1.setShopId(9L);
        Shop shop2 = new Shop();
        shop2.setShopId(13L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(3L);
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryId(6L);
        Product product1 = new Product();
        product1.setProductName("苹果汁");
        product1.setProductDesc("鲜榨苹果汁1杯");
        product1.setImgAddr("test1");
        product1.setPriority(0);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("土豆大风暴套餐");
        product2.setProductDesc("切克闹来一套");
        product2.setImgAddr("test2");
        product2.setPriority(0);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc2);

        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
    }


    @Test
    public void testQueryProductList() throws Exception {
        Product product = new Product();
        List<Product> productList = productDao.queryProductList(product, 0, 100);
        System.out.println(JSONUtil.stringify(productList));
        System.out.println("productList.size()=" + productList.size());
//		assertEquals(3, productList.size());
        int count = productDao.queryProductCount(product);
        System.out.println("count=" + count);
//		assertEquals(4, count);
//		product.setProductName("苹果汁");
        productList = productDao.queryProductList(product, 0, 100);
        System.out.println("productList.size()=" + productList.size());
        count = productDao.queryProductCount(product);
        System.out.println("count=" + count);
//		assertEquals(3, count);
        Shop shop = new Shop();
        shop.setShopId(9L);
        product.setShop(shop);
        productList = productDao.queryProductList(product, 0, 100);
        System.out.println("productList.size()=" + productList.size());
//		assertEquals(1, productList.size());
        count = productDao.queryProductCount(product);
        System.out.println("count=" + count);
//		assertEquals(1, count);
    }


    @Test
    public void testupdateProductCategoryToNull() throws Exception {
		int effectedNum = productDao.updateProductCategoryToNull(20L);
		assertEquals(2, effectedNum);
    }

}
