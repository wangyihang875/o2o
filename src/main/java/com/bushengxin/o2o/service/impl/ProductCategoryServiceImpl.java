package com.bushengxin.o2o.service.impl;

import com.bushengxin.o2o.dao.ProductCategoryDao;
import com.bushengxin.o2o.dao.ProductDao;
import com.bushengxin.o2o.dao.ShopCategoryDao;
import com.bushengxin.o2o.dto.ProductCategoryExecution;
import com.bushengxin.o2o.entity.ProductCategory;
import com.bushengxin.o2o.entity.ShopCategory;
import com.bushengxin.o2o.enums.ProductCategoryStateEnum;
import com.bushengxin.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    /**
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategory(shopId);
    }

    /**
     * @param productCategoryList@return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws RuntimeException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new RuntimeException("创建店铺类别失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new RuntimeException("batchAddProductCategory error: " + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
        }
    }

    /**
     * 先将此类别下的商品里的类别id置为空，再删除该类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws RuntimeException {
        //先将此类别下的商品里的类别id置为空，再删除该类别
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum < 0) {
                throw new RuntimeException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error: " + e.getMessage());
        }
        //删除商品类别
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new RuntimeException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error:" + e.getMessage());
        }
    }
}
