package com.bushengxin.o2o.service;

import com.bushengxin.o2o.BaseTest;
import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.Area;
import com.bushengxin.o2o.entity.PersonInfo;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.entity.ShopCategory;
import com.bushengxin.o2o.enums.ShopStateEnum;
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
        FileItem fileItem = createFileItem(shopImg, "shopImg");
        CommonsMultipartFile xxCMF = new CommonsMultipartFile(fileItem);

        ShopExecution se = shopService.addShop(shop, xxCMF);
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
//
//	@Ignore
//	@Test
//	public void testGetByShopId() throws Exception {
//		long shopId = 1;
//		Shop shop = shopService.getByShopId(shopId);
//		System.out.println(shop);
//	}

    //把File转化为CommonsMultipartFile
    public FileItem createFileItem(File file, String fieldName) {
        //DiskFileItemFactory()：构造一个配置好的该类的实例
        //第一个参数threshold(阈值)：以字节为单位.在该阈值之下的item会被存储在内存中，在该阈值之上的item会被当做文件存储
        //第二个参数data repository：将在其中创建文件的目录.用于配置在创建文件项目时，当文件项目大于临界值时使用的临时文件夹，默认采用系统默认的临时文件路径
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        //fieldName：表单字段的名称；第二个参数 ContentType；第三个参数isFormField；第四个：文件名
        FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);//从buffer中得到数据进行写操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
