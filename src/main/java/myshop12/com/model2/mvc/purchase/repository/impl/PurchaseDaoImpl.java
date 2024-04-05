package myshop12.com.model2.mvc.purchase.repository.impl;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.repository.PurchaseDao;
import myshop12.com.model2.mvc.purchase.repository.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

    ///Fields
    private final PurchaseMapper purchaseMapper;

    ///Constructor
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public PurchaseDaoImpl(@Qualifier("purchaseMapper") PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }
    ///Methods
    public void addPurchase(Purchase purchase) throws Exception {
        purchaseMapper.addPurchase(purchase);
        final int tranNo = purchase.getTranNo();
        purchase.getPurchaseDetailList().forEach(purchaseDetail -> {
            try {
                purchaseMapper.addPurchaseDetail(purchaseDetail);
            } catch (Exception e) {
                // 예외 처리 로직
                e.printStackTrace();
            }
        });
    }

    public Purchase getPurchase(int tranNo) throws Exception {
        return purchaseMapper.getPurchase(tranNo);
    }
    public Purchase getPurchaseProdNo(int prodNo) throws Exception {
        return purchaseMapper.getPurchaseProdNo(prodNo);
    }

    public Map<String, Object> getPurchaseList(Map<String,Object> map) throws Exception {
        List<Map<String,Object>> list = purchaseMapper.getPurchaseList(map);
        List<Purchase> purchaseList = null;
        System.out.println("LIST :: "+list);


        int count = 0;

        if(!list.isEmpty()) {
            count = ((BigDecimal) list.get(0).get("count")).intValue();
            purchaseList = new ArrayList<Purchase>();
            for (Map<String, Object> purchaseMap : list) {
                System.out.println("purchaseMap.get(purchase) :: " + purchaseMap.get("purchase"));
                purchaseList.add((Purchase) purchaseMap.get("purchase"));
            }

        }
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("list", purchaseList);
        map2.put("count", count);
        return map2;
    }

    public Map<String, Object> getSaleList(Search search) throws Exception {
        return purchaseMapper.getSaleList(search);
    }

    public Purchase updatePurchase(Purchase purchase) throws Exception {
        purchaseMapper.updatePurchase(purchase);
        return purchase;
    }

    public void updateTranCode(Purchase purchase) throws Exception {
        purchaseMapper.updateTranCode(purchase);
    }




}
