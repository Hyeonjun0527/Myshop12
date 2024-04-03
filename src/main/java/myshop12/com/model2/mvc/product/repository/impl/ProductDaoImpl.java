package myshop12.com.model2.mvc.product.repository.impl;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.repository.ProductDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//==> 회원관리 DAO CRUD 구현
@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ProductDaoImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public void insertProduct(Product product) throws Exception {
		//제너레이트 키로 인해 도메인이 수정된다.
		sqlSession.insert("ProductMapper.insertProduct", product);

	}

	public Product findProduct(int prodNo) throws Exception {
		return sqlSession.selectOne("ProductMapper.findProduct", prodNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		List<Product> list = sqlSession.selectList("ProductMapper.getProductList", search);
		int totalCount = sqlSession.selectOne("ProductMapper.getTotalCount", search);

		System.out.println("ProductDaoImpl.getProductList()::"+list);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));

		return map;
	}
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	//getTotalCount는 따로 구현하지 않음. totalcount는 getProductList에서 구현함

}