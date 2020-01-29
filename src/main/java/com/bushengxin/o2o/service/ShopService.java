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
	 * @throws Exception
	 */
	ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);


}
