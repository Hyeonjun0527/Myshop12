package myshop12.com.model2.mvc.purchase.domain;


import lombok.*;
import myshop12.com.model2.mvc.product.domain.Product;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseDetail {
    private int detailNo;//KEY
    private int tranNo;//KEY
    private Product product;
    private int typeQuantity;//구매한 개수
    private int typePrice;//구매한 개수 * 상품가격
    //얘는 한번에 구매한 구매목록중에 하나의 상품에 대해서이다.
    //얘를 들어 자전거3개 보르도 4개 샀으면
    //자전거 3개의 가격이 typePrice이다.

    public void setTypeQuantity(int typeQuantity){
        this.typeQuantity = typeQuantity;
        this.typePrice = this.typeQuantity * this.product.getPrice();
    }

    //얘는 jsp로부터 바인딩하는 도메인이 아니야.
    //Purchase로 부터 바인딩하는 도메인이야.
}
