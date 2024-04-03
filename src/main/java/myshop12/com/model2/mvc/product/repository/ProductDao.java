package myshop12.com.model2.mvc.product.repository;


import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;

import java.util.Map;


//==> 회원관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
public interface ProductDao {

	void insertProduct(Product product) throws Exception;
	public Product findProduct(int prodNo) throws Exception;
	public Map<String,Object> getProductList(Search search) throws Exception;
	public void updateProduct(Product productVO) throws Exception;

}