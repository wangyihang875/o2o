package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    /*
    * 根据shopId查询产品分类
    * @param shopId
    * @return List<ProductCategory>
     * */
    List<ProductCategory> queryProductCategory(long shopId);

    /**
     * 批量新增商品类别
     *
     * @param ProductCategory productCategory
     * @return effectedNum
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商品类别（初版，即只支持删除尚且没有发布商品的商品类别）
     *
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
