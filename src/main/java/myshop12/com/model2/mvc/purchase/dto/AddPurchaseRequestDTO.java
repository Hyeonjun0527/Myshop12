package myshop12.com.model2.mvc.purchase.dto;

import lombok.*;
import myshop12.com.model2.mvc.purchase.domain.PurchaseDetail;
import myshop12.com.model2.mvc.user.domain.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPurchaseRequestDTO {//얘는 하나만 구매하는 경우다.
    private User buyer;//PK
    private String divyAddr;//배송주소receiverAddr
    private String divyDate;//배송희망일자
    private String divyRequest;//구매요청사항
    private Date orderDate;//주문일 sysdate
    private String paymentOption;
    //"현금구매" "신용구매"
    private String receiverName;//구매자이름
    private String receiverPhone;//구매자연락처
    private String tranCode;
    private int totalPrice;//총가격
    private List<PurchaseDetail> purchaseDetailList;//
    private List<Integer> totalCount;//구매 개수

    public void calculateTotalPrice(){
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            this.totalPrice+=purchaseDetail.getTypePrice();
        }
    }
}
