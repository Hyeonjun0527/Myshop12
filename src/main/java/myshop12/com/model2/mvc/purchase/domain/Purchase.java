package myshop12.com.model2.mvc.purchase.domain;

import lombok.*;
import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.product.service.ProductService;
import myshop12.com.model2.mvc.user.domain.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//has a 한건 인스턴스화 해서 쓰면
//동기화문제가 발생한다는건 has a 되있는 자기필드를 자기 메서드에서 썼을때 
//싱글톤?

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {//필든느 와스서버메모리 공용메모리 use관계 인스턴스화해서 쓰는 관계는 인스턴는 복제본
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[91m";
	private int tranNo;//PK
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
	private int totalPrice;//총 가격
	private List<PurchaseDetail> purchaseDetailList;//

	//객체에 색 입혀서 투스트링
	public String toString(){
		return RED+"Purchase"+RESET+ "[tranNo=" + tranNo + ", buyer=" + buyer + ", divyAddr=" + divyAddr + ", divyDate=" + divyDate + ", divyRequest=" + divyRequest + ", orderDate=" + orderDate + ", paymentOption=" + paymentOption + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone + ", tranCode=" + tranCode + ", totalPrice=" + totalPrice + ", purchaseDetailList=" + purchaseDetailList + "]";
	}

//TRAN_STATUS_CODE임
	// 판매중/구매완료 배송하기/ 배송중 / 배송완료
}