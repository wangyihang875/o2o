package com.bushengxin.o2o.service;

import com.bushengxin.o2o.entity.ShopCategory;
import java.io.IOException;
import java.util.List;

public interface ShopCategoryService {

	/**
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
