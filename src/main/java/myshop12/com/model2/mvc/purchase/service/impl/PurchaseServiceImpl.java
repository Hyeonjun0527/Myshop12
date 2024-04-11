package myshop12.com.model2.mvc.purchase.service.impl;

import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.repository.ProductDao;
import myshop12.com.model2.mvc.product.service.ProductService;
import myshop12.com.model2.mvc.product.service.impl.ProductServiceImpl;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;
import myshop12.com.model2.mvc.purchase.mapper.PurchaseMapper;
import myshop12.com.model2.mvc.purchase.repository.PurchaseDao;
import myshop12.com.model2.mvc.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
    //// Fields
    private final PurchaseDao purchaseDao;
    private final ProductDao productDao;
    /// Setters
//    @Autowired
//    public void setPurchaseDao(@Qualifier("purchaseDaoImpl") PurchaseDao purchaseDao) {
//        this.purchaseDao = purchaseDao;
//    }
    // Constructors
    @Autowired
    public PurchaseServiceImpl(@Qualifier("purchaseDao") PurchaseDao purchaseDao,
                                    @Qualifier("productDaoImpl") ProductDao productDao) {
        this.purchaseDao = purchaseDao;
        this.productDao = productDao;
    }
    // Methods
    public void addPurchase(AddPurchaseRequestDTO dto) throws Exception {
        //여기서 mapStruct를 사용하여 addPurchaseRequestDTO를 purchase로 변환
        //여기서 매핑을 하자
        //totalPrice를 계산
        dto.calculateTotalPrice();
        System.out.println("PurchaseServiceImpl dto :: "+dto);


        Purchase purchase = PurchaseMapper.INSTANCE.toEntity(dto);
        purchase.setTranCode("b");
        System.out.println(purchase);
        purchaseDao.addPurchase(purchase);//얘는 제너레이트 키로 tranNo를 생성,할당
        final int tranNo = purchase.getTranNo();

        //리스트의 forEach문으로 요소마다 addPurchaseDetail 메서드를 호출
        purchase.getPurchaseDetailList().forEach(purchaseDetail -> {
            try {
                purchaseDetail.setTranNo(tranNo);
                purchaseDao.addPurchaseDetail(purchaseDetail);//detailNo를 생성,할당
                System.out.println("purchaseDetail.getProduct().getStockQuantity() : " + purchaseDetail.getProduct().getStockQuantity());
                System.out.println("purchaseDetail.getTypeQuantity() : " + purchaseDetail.getTypeQuantity());
                int newStockQuantity = purchaseDetail.getProduct().getStockQuantity() - purchaseDetail.getTypeQuantity();
                System.out.println("newStockQuantity : " + newStockQuantity);
                Product product = new Product();
                product = purchaseDetail.getProduct();
                System.out.println("product before : " + product);
                product.setStockQuantity(newStockQuantity);
                System.out.println("product after : " + product);
                productDao.updateProductStock(product);
            } catch (Exception e) {
                // 예외 처리 로직
                e.printStackTrace();
            }
        });
    }
    public Purchase getPurchase(int tranNo) throws Exception {
        return purchaseDao.getPurchase(tranNo);
    }
    public Map<String, Object> getPurchaseList(Map<String,Object> map) throws Exception {
        List<Map<String,Object>> list = purchaseDao.getPurchaseList(map);
        List<Purchase> purchaseList = null;
        System.out.println("LIST :: "+list);//여기서 product=null,typeQuantity=0이야..


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
        return purchaseDao.getSaleList(search);
    }
    public Purchase updatePurchase(Purchase purchase) throws Exception {
        purchaseDao.updatePurchase(purchase);
        return purchase;
    }
    public void updateTranCode(Purchase purchase) throws Exception {
        purchaseDao.updateTranCode(purchase);
    }

}
