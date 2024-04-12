package myshop12.com.model2.mvc.purchase.repository;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseDao {

    public void addPurchase(Purchase purchase) throws Exception;
    public void addPurchaseDetail(PurchaseDetail purchaseDetail) throws Exception;
    public Purchase getPurchase(int tranNo) throws Exception;
    public List<Map<String,Object>> getPurchaseList(@Param("map") Map<String,Object> map) throws Exception;
    public List<Map<String,Object>> getSaleList(@Param("map") Map<String,Object> map) throws Exception;
    public void updatePurchase(Purchase purchase) throws Exception;
//    public void updatePurchaseDetail(PurchaseDetail purchaseDetail) throws Exception;
    public void updateTranCode(Purchase purchase) throws Exception;



    /*
    * resultMap의 타입이 Map이면 컬럼이 1개일때 Map을 리턴하고 컬럼이 n개면 List<Map>을 리턴한다. 이하동문
    * purchaseSelectMap과 purchaseSelectUnit이 있는데,
    purchaseSelectMap을 쓰면 count를 가져올 수 있고, purchaseSelectUnit을 쓰면 count를 가져올 수 없다.
    * purchaseSelectUnit을 쓰면서 count를 가져오려면 count속성을 추가한 임시도메인을 만들거나 쿼리를 하나 새로 파야함
    * Map을 쓰면 단점이 컬럼1개인 Map이 생겨서 쓸데없이 count가 컬럼개수만큼 들어간다..
    *
     */
}
