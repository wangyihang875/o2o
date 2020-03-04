package com.bushengxin.o2o.service.impl;

import com.bushengxin.o2o.dao.ShopDao;
import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.PersonInfo;
import com.bushengxin.o2o.entity.Shop;
import com.bushengxin.o2o.enums.ShopStateEnum;
import com.bushengxin.o2o.exceptions.ShopOperationException;
import com.bushengxin.o2o.service.ShopService;
import com.bushengxin.o2o.util.FileUtil;
import com.bushengxin.o2o.util.ImageUtil;
import com.bushengxin.o2o.util.PageCalculator;
import com.bushengxin.o2o.web.superadmin.AreaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopDao shopDao;


    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException {
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
                    e.printStackTrace();
                    throw new RuntimeException("创建图片地址失败: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("insertShop error: " + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);

    }

    /**
     * 查询指定店铺信息
     *
     * @param shopId@return Shop shop
     */
    @Override
    public Shop getShopById(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     * 更新店铺信息（从店家角度）
     *
     * @param shop
     * @param shopImg
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        } else {
            try {
                //1.判断是否需要处理图片
                if (shopImg != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        FileUtil.deleteFile(tempShop.getShopImg());//我认为应该在最后删除原图，因为删除图片的操作无法回滚
                    }
                    addShopImg(shop, shopImg);
                }
                //1.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    // 创建成功
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("modifyShop error: " + e.getMessage());
            }
        }
    }

    /**
     * 根据shopCondition分页返回相应数据
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return ShopExecution
     * @paramint int pageIndex
     * @paramint int pageSize
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 查询该用户下面的店铺信息
     *
     * @param employeeId@return List<Shop>
     * @throws Exception
     */
    @Override
    public ShopExecution getShopByEmployeeId(long employeeId) throws RuntimeException {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(employeeId);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 100);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }


    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
