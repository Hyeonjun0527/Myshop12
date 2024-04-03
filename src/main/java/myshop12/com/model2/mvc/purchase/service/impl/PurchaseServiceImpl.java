package myshop12.com.model2.mvc.purchase.service.impl;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.repository.PurchaseDao;
import myshop12.com.model2.mvc.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
    //// Fields
    private PurchaseDao purchaseDao;
    /// Setters
    @Autowired
    public void setPurchaseDao(@Qualifier("purchaseDaoImpl") PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }
    // Constructors
    public PurchaseServiceImpl() {
    }
    // Methods
    public void addPurchase(Purchase purchase) throws Exception {
        purchaseDao.addPurchase(purchase);

    }
    public Purchase getPurchase(int tranNo) throws Exception {
        return purchaseDao.getPurchase(tranNo);
    }
    public Purchase getPurchaseProdNo(int prodNo) throws Exception {
        return purchaseDao.getPurchaseProdNo(prodNo);
    }
    public Map<String, Object> getPurchaseList(Map<String,Object> map) throws Exception {
        return purchaseDao.getPurchaseList(map);
    }
    public Map<String, Object> getSaleList(Search search) throws Exception {
        return purchaseDao.getSaleList(search);
    }
    public Purchase updatePurchase(Purchase purchase) throws Exception {
        return purchaseDao.updatePurchase(purchase);
    }
    public void updateTranCode(Purchase purchase) throws Exception {
        purchaseDao.updateTranCode(purchase);
    }

}
