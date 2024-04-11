package myshop12.com.model2.mvc.product.service.impl;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.repository.ProductDao;
import myshop12.com.model2.mvc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	///Field
	private ProductDao productDao;

	@Autowired
	public void setProductDao(@Qualifier("productDaoImpl") ProductDao productDao) {
		this.productDao = productDao;
	}

	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public Product addProduct(Product product) throws Exception {
		productDao.insertProduct(product);

		//판매중으로 바꿔야함
		product.setProTranCode("a");

		return product;
	}
	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}
	public Product updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);//원래트랜코드도 유지해 왜냐면 원래있던거를 가져다가 수정해서 건내주기때문
		return  product;
	}
	public Product updateProductStock(Product product) throws Exception {
		productDao.updateProductStock(product);
		return product;
	}
	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDao.getProductList(search);
	}
}