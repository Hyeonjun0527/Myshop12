package myshop12.com.model2.mvc.purchase.controller;

import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.purchase.domain.Purchase;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import myshop12.com.model2.mvc.purchase.dto.AddPurchaseRequestDTO;

import java.util.ArrayList;
import java.util.List;

class PurchaseUtil {

//    //하나의 구매에 다양한 제품을 구매.
//    public static List<PurchaseDetail> createPurchaseDetailList(AddPurchaseRequestDTO addPurchaseRequestDTO, List<Product> productList) {
//        //detailNo는 제너레이트 키이므로 null
//        //빌더사용
//
//        List<PurchaseDetail> purchaseDetailList = new ArrayList<>();
//        //리스트를 반복
//        productList.forEach(product->{
//            PurchaseDetail purchaseDetail = PurchaseDetail.builder()
//                    .product(product)
//
//                    .build();
//
//
//        });
//        return
//    }

    public static PurchaseDetail createPurchaseDetail(AddPurchaseRequestDTO addPurchaseRequestDTO, Product product){

        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setProduct(product);
        purchaseDetail.setTypeQuantity(addPurchaseRequestDTO.getTotalCount());

        return purchaseDetail;

    }

}
