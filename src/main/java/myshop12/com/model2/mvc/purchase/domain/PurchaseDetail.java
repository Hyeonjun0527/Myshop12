package myshop12.com.model2.mvc.purchase.domain;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseDetail {
    private int detailNo;//KEY
    private int tranNo;//KEY
    private int prodNo;//KEY
    private int typeQuantity;
    private int typePrice;
}
