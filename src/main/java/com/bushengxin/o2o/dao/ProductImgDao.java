package com.bushengxin.o2o.dao;

import com.bushengxin.o2o.entity.ProductImg;
import java.util.List;

public interface ProductImgDao {


	/*
	* 批量添加商品详情图片
	* */
	int batchInsertProductImg(List<ProductImg> productImgList);

	List<ProductImg> queryProductImgList(long productId);

	int deleteProductImgByProductId(long productId);
}
