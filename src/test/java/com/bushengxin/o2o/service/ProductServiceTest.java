package com.bushengxin.o2o.service;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.dto.ProductExecution;
import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.*;
import com.bushengxin.o2o.enums.ShopStateEnum;
import com.bushengxin.o2o.util.FileUtil;
import com.bushengxin.o2o.util.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws Exception {
        Shop shop = new Shop();
        shop.setShopId(13L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(6L);
        Product product1 = new Product();
        product1.setProductName("缤纷全家桶");
        product1.setProductDesc("6种小吃");
//        product1.setImgAddr("test1");
        product1.setPriority(0);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop);
        product1.setProductCategory(pc);
        //创建缩略图
        File thumbnailFile = new File("/Users/wangyihang/Pictures/com.bushengxin.o2o/bird.jpg");
        FileItem thumbnailFileItem = FileUtil.createFileItem(thumbnailFile, "imgAddr");
        CommonsMultipartFile thumbnailFileCM = new CommonsMultipartFile(thumbnailFileItem);
        //创建2个商品详情图
        File productImgFile = new File("/Users/wangyihang/Pictures/com.bushengxin.o2o/stars.jpg");
        FileItem productImgFileItem = FileUtil.createFileItem(productImgFile, "imgAddr");
        CommonsMultipartFile productImgFileItemCM = new CommonsMultipartFile(productImgFileItem);

        File productImgFile2 = new File("/Users/wangyihang/Pictures/com.bushengxin.o2o/sun.jpg");
        FileItem productImgFileItem2 = FileUtil.createFileItem(productImgFile2, "imgAddr");
        CommonsMultipartFile productImgFileItemCM2 = new CommonsMultipartFile(productImgFileItem2);

        List<CommonsMultipartFile> productImgList = new ArrayList<CommonsMultipartFile>();
        productImgList.add(productImgFileItemCM);
        productImgList.add(productImgFileItemCM2);

        ProductExecution pe =  productService.addProduct(product1,thumbnailFileCM,productImgList);
        ObjectMapper mapper = new ObjectMapper();
        String peJson = mapper.writeValueAsString(pe);
        System.out.println(peJson);
    }

    @Test
    public void getProductById(){
        Product p = productService.getProductById(3L);
        System.out.println(JSONUtil.stringify(p));
    }

}
