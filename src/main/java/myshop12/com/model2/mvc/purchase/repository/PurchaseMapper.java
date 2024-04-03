package myshop12.com.model2.mvc.purchase.repository;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseMapper {

    public void addPurchase(Purchase purchase) throws Exception;
    public Purchase getPurchase(int tranNo) throws Exception;
    public Purchase getPurchaseProdNo(int prodNo) throws Exception;
    public List<Map<String,Object>> getPurchaseList(@Param("map") Map<String,Object> map) throws Exception;
    public Map<String,Object> getSaleList(Search search) throws Exception;
    public void updatePurchase(Purchase purchase) throws Exception;
    public void updateTranCode(Purchase purchase) throws Exception;
}
