package myshop12.com.model2.mvc.purchase.domain;

import myshop12.com.model2.mvc.product.domain.Product;
import myshop12.com.model2.mvc.user.domain.User;

import java.sql.Date;

//has a 한건 인스턴스화 해서 쓰면
//동기화문제가 발생한다는건 has a 되있는 자기필드를 자기 메서드에서 썼을때 
//싱글톤?

public class Purchase {//필든느 와스서버메모리 공용메모리 use관계 인스턴스화해서 쓰는 관계는 인스턴는 복제본 
	
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
	//TRAN_STATUS_CODE임
	// 판매중/구매완료 배송하기/ 배송중 / 배송완료
	private int tranNo;//PK
	
	public Purchase(){
	}
	
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public String getDivyAddr() {
		return divyAddr;
	}
	public void setDivyAddr(String divyAddr) {
		this.divyAddr = divyAddr;
	}
	public String getDivyDate() {
		return divyDate;
	}
	public void setDivyDate(String divyDate) {
		this.divyDate = divyDate;
	}
	public String getDivyRequest() {
		return divyRequest;
	}
	public void setDivyRequest(String divyRequest) {
		this.divyRequest = divyRequest;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public Product getPurchaseProd() {
		return purchaseProd;
	}
	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
	@Override
	public String toString() {
		return "Purchase [buyer=" + buyer + ", divyAddr=" + divyAddr + ", divyDate=" + divyDate + ", divyRequest="
				+ divyRequest + ", orderDate=" + orderDate + ", paymentOption=" + paymentOption + ", purchaseProd="
				+ purchaseProd + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone + ", tranCode="
				+ tranCode + ", tranNo=" + tranNo + "]";
	}
}