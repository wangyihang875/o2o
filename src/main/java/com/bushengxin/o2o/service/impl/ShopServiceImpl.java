package com.bushengxin.o2o.service.impl;

import com.bushengxin.o2o.dao.ShopDao;
import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.enums.ShopStateEnum;
import com.bushengxin.o2o.service.ShopService;
import com.bushengxin.o2o.util.FileUtil;
import com.bushengxin.o2o.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;


    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                //只有抛出RuntimeException或其子类时事务才会终止并且回滚
                throw new RuntimeException("店铺创建失败");
            } else {
                try {
                    if (shopImg != null) {
                        //存储图片
                        /*
                         *对于java的所有方法来说，参数是基本类型，传递的是自变量值的拷贝，在方法里对参数值的改变不会影响到这个参数实际的值
                         * 如果参数是引用类型，传递的就是参数所引用的对象在堆中地址的拷贝，在方法里对参数对象的改变会影响到外面
                         *  */
                        addShopImg(shop, shopImg);
                        //更新店铺的图片地址
                        effectedNum = shopDao.updateShop(shop);

                        if (effectedNum <= 0) {
                            throw new RuntimeException("更新图片地址失败");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("创建图片地址失败: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("insertShop error: " + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);

    }

    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
