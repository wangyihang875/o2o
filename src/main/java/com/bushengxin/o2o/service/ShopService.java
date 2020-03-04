package com.bushengxin.o2o.service;

import com.bushengxin.o2o.dto.ShopExecution;
import com.bushengxin.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface ShopService {


	/**
	 * 创建商铺
	 * 
	 * @param Shop shop
	 * @return ShopExecution shopExecution
	 * @throws RuntimeException
	 */
	ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg)throws RuntimeException;

	/**
	 * 查询指定店铺信息
	 *
	 * @param shopId
	 * @return Shop shop
	 */
	Shop getShopById(long shopId);

	/**
	 * 更新店铺信息（从店家角度）
	 *
	 * @param areaId
	 * @param shopAddr
	 * @param phone
	 * @param shopImg
	 * @param shopDesc
	 * @return
	 * @throws RuntimeException
	 */
	ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException;

	/**
	 * 根据shopCondition分页返回相应数据
	 *
	 * @param Shop shopCondition
	 * @paramint int pageIndex
	 * @paramint int pageSize
	 * @return ShopExecution
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

	/**
	 * 查询该用户下面的店铺信息
	 *
	 * @param long employyeeId
	 * @return List<Shop>
	 * @throws Exception
	 */
	ShopExecution getShopByEmployeeId(long employeeId) throws RuntimeException;

}
