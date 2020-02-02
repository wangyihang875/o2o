package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 通过shop id 查询店铺
	 *
	 * @param shopId
	 * @return shop
	 */
	Shop queryByShopId(long shopId);
	/**
	 * 新增店铺
	 *
	 * @param shop
	 * @return effectedNum
	 */
	int insertShop(Shop shop);

	/**
	 * 更新店铺信息
	 *
	 * @param shop
	 * @return effectedNum
	 */
	int updateShop(Shop shop);
}
