package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ShopCategoryDao {
	/**
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(
            @Param("shopCategoryCondition") ShopCategory shopCategoryCondition);



}
