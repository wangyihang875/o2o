package com.bushengxin.o2o.service;

import com.bushengxin.o2o.dto.ShopAuthMapExecution;
import com.bushengxin.o2o.entity.ShopAuthMap;

public interface ShopAuthMapService {
	/**
	 * 
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,
												 Integer pageIndex, Integer pageSize);

	/**
	 * 
	 * @param shopAuthMap
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException;

	/**
	 * 更新授权信息，包括职位等
	 * 
	 * @param shopAuthId
	 * @param title
	 * @param titleFlag
	 * @param enableStatus
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException;

	/**
	 * 
	 * @param shopAuthMapId
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId)
			throws RuntimeException;

	/**
	 * 
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);

}
