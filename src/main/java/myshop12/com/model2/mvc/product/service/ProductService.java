package myshop12.com.model2.mvc.product.service;


import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;

import java.util.Map;

public interface ProductService {

	public Product addProduct(Product productVO) throws Exception;
	public Product getProduct(int productNo) throws Exception;
	public Map<String, Object> getProductList(Search search) throws Exception;
	public Product updateProduct(Product productVO) throws Exception;
}
