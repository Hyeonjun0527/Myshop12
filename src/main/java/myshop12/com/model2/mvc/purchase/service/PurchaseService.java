package myshop12.com.model2.mvc.purchase.service;


import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

    public
    void addPurchase(AddPurchaseRequestDTO purchaseDTO) throws Exception;

    public Purchase getPurchase(int tranNo) throws Exception;
    public Map<String, Object> getPurchaseList(Map<String, Object> map) throws Exception;

    public Map<String, Object> getSaleList(Map<String,Object> map) throws Exception;

    public Purchase updatePurchase(Purchase purchase) throws Exception ;

    public void updateTranCode(Purchase purchase) throws Exception;



}
