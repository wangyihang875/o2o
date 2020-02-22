package com.bushengxin.o2o.service;

import com.bushengxin.o2o.dto.ProductCategoryExecution;
import com.bushengxin.o2o.entity.ProductCategory;
import java.util.List;

public interface ProductCategoryService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	/**
	 *
	 * @param productCategory
	 * @return
	 * @throws RuntimeException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws RuntimeException;

	/**
	 * 先将此类别下的商品里的类别id置为空，再删除该类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws RuntimeException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws RuntimeException;
}
