package myshop12.com.model2.mvc.purchase.domain;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseDetail {
    private int detailNo;
    private int tranNo;
    private int prodNo;
    private int typeQuantity;
    private int typePrice;
}
