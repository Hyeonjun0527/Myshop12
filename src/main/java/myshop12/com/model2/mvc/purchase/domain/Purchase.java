package myshop12.com.model2.mvc.purchase.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.user.domain.User;

import java.sql.Date;
import java.util.List;

//has a 한건 인스턴스화 해서 쓰면
//동기화문제가 발생한다는건 has a 되있는 자기필드를 자기 메서드에서 썼을때 
//싱글톤?

@Getter
@Setter
@ToString
@Builder
public class Purchase {//필든느 와스서버메모리 공용메모리 use관계 인스턴스화해서 쓰는 관계는 인스턴는 복제본

	private int tranNo;//PK
	private User buyer;//PK
	private String divyAddr;//배송주소receiverAddr
	private String divyDate;//배송희망일자
	private String divyRequest;//구매요청사항
	private Date orderDate;//주문일 sysdate
	private String paymentOption;
	//"현금구매" "신용구매"
	private Product purchaseProd;//PK
	private String receiverName;//구매자이름
	private String receiverPhone;//구매자연락처
	private String tranCode;

	private String totalPrice;
	private List<PurchaseDetail> purchaseDetailList;
//TRAN_STATUS_CODE임
	// 판매중/구매완료 배송하기/ 배송중 / 배송완료

	public Purchase(){
	}
}