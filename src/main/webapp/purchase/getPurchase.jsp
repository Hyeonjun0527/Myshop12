<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 상품 상세 조회-->

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
<title>Insert title here</title>
	<%--제이쿼리--%>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<%--부트스트랩--%>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<%-- 부트스트랩 Dropdown Hover CSS JS--%>
	<link href="/css/animate.min.css" rel="stylesheet">
	<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
	<script src="/js/bootstrap-dropdownhover.min.js"></script>


	<script type="text/javascript" src="../js/calendar.js">
		<%--	..으로 상대경로로 경로지정.--%>
	</script>
	<link rel="stylesheet" href="/css/font.css" type="text/css">
	<style>
		body {
			padding-top: 50px;
		}
		.size-set{
			width:1140px;
			margin-left: 15px;
		}
		.max-size {
			max-width: 100px !important;
			max-height: 100px !important;
		}
		.no-padding{
			padding: 0;
		}
	</style>
</head>

<body class="default-font">
<!-- ToolBar Start /////////////////////////////////////-->
<jsp:include page="/layout/toolbar.jsp" />
<!-- ToolBar End /////////////////////////////////////-->

<!--  화면구성 div Start /////////////////////////////////////-->
<div class="container">
<form name="updatePurchase" action="/purchase/updatePurchase?tranNo=1234321" method="post">
	<div class="page-header">
		<h3 class=" text-info">다음과 같이 구매가 되었습니다.</h3>
	</div>
	<div class="row">
<%--		<div class="col-xs-8 col-md-4">구매번호 : ${purchase.tranNo}</div>--%>

		<c:forEach var="purchaseDetail" items="${purchase.purchaseDetailList}">
			<div class="col-xs-8 col-md-4">구매물품번호 : ${purchaseDetail.detailNo}</div>
			<div class="col-xs-8 col-md-4">상품이름 : ${purchaseDetail.product.prodName}</div>
			<div class="col-xs-8 col-md-4">상품번호 : ${purchaseDetail.product.prodNo}</div>
			<div class="col-xs-8 col-md-4">구매한 개수 : ${purchaseDetail.typeQuantity}</div>
			<div class="col-xs-8 col-md-4">상품별 가격 : ${purchaseDetail.typePrice}</div>
		</c:forEach>
	</div>

	<hr/>

	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>구매자아이디</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.buyer.userId}</div>
	</div>

	<hr/>

	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>구매방법</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.paymentOption}</div>
	</div>

	<hr/>

	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>구매자이름</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.receiverName}	</div>
	</div>

	<hr/>

	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>구매자연락처</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.receiverPhone}</div>
	</div>

	<hr/>

	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>구매자주소</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.divyAddr}</div>
	</div>

	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>구매요청사항</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.divyRequest}</div>
	</div>
	<div class="row">
		<div class="col-xs-4 col-md-2 "><strong>배송희망일자</strong></div>
		<div class="col-xs-8 col-md-4">${purchase.divyDate}</div>
	</div>



	<hr/>
	<div class="row">
		<div class="col-md-12 text-center ">
			<button type="button" class="btn btn-primary confirm">구매취소</button>
		</div>
	</div>

</form>

<a href="/main.jsp">메인으로 가기. 리로드 누르지 말아주세요</a>
</div>
</body>
</html>