package com.bushengxin.o2o.service;

import com.bushengxin.o2o.dto.ProductExecution;
import com.bushengxin.o2o.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.util.List;

public interface ProductService {

	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws RuntimeException;

	Product getProductById(long productId);

	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws RuntimeException;

}
