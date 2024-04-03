<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>판매/구매 목록조회</title>


<link rel="stylesheet" href="/css/listPurchase.css" type="text/css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">


	function fncGetList(currentPage) {
		if(currentPage===undefined){
			currentPage = 1;
		}
			$("#currentPage").val(currentPage);
			$("form[name='detailForm']")
					.attr("method", "post")
					.attr("action", "/purchase/listPurchase?menu=${param.menu}")
					.submit();
	}//end of fncGetList

	$(document).ready(function () {
		$("span.clickableSpan").click(function () {
			console.log("클릭했어요");
			if (typeof $(this).data("getpurchase") !== "undefined") {
				console.log("getPurchase");
				let prodNo = $(this).data("prodno");
				$("form[name='detailForm']")
						.attr("method", "post")
						.attr("action", "/product/getProduct?prodNo=" + prodNo +"&menu=${param.menu}")
						.submit();
			} else if (typeof $(this).data("getuser") !== "undefined") {
				console.log("getUser");
				let userId = $(this).data("userid");
				console.log(userId);
				window.location.href = "/user/getUser"+"?userId=" + userId;
			} else if (typeof $(this).data("update") !== "undefined") {
				console.log("update");
				let prodNo = $(this).data("prodno");
				$("form[name='detailForm']")
						.attr("method", "post")
						.attr("action", "/purchase/updatePurchase?prodNo=" + prodNo + "&menu=${param.menu}" + "&navigationPage=listPurchase")
						.submit();
			}
		});
	})//end of ready

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>${param.menu=='manage' ? '판매 목록조회' :'구매 목록조회'}
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${totalCount} 건수, 현재
						${requestScope.resultPage.currentPage} 페이지
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원ID</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">전화번호</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">배송현황</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">정보수정</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>

				<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${requestScope.list}">
					<c:set var="i" value="${i+1 }" />
					<tr class="ct_list_pop">
						<td align="center">
						<c:if test="${purchase.purchaseProd.prodNo!=0 }">
							<span class="clickableSpan" data-getPurchase data-prodNo="${purchase.purchaseProd.prodNo}">${purchase.purchaseProd.prodNo}</span>
						</c:if>
						</td>
						<td></td>
						<td align="left">
							<span class="clickableSpan" data-getUser data-userId="${purchase.buyer.userId}">${purchase.buyer.userId}</span>
						</td>
						<td></td>
						<td align="left">${purchase.receiverName}</td>
						<td></td>
						<td align="left">${purchase.receiverPhone}</td>
						<td></td>
						<c:if test="${purchase.tranCode!=null}">

							<c:set var="resultA"
								value="${purchase.tranCode.trim() == 'a' ? '판매중' : ''}" />
							<c:set var="resultB"
								value="${purchase.tranCode.trim() == 'b' ? '구매완료' : ''}" />
<%--
							<c:if test="${param.menu == 'manage' || param.menu == 'ok'}">
								<c:set var="resultB2"
									value="${purchase.tranCode.trim() == 'b' ? '배송하기' : ''}" />
							</c:if>
	--%>
							<c:set var="resultC"
								value="${purchase.tranCode.trim() == 'c' ? '배송중' : ''}" />
							<c:set var="resultD"
								value="${purchase.tranCode.trim() == 'd' ? '배송완료' : ''}" />

<%--현재 ${resultA}${resultB}${(!empty resultB) ? '&nbsp;&nbsp;' : ''} --%>
							<td align="left">현재 ${resultA}${resultB}
								<%--<span class="clickableSpan" data->${resultB2}</span>--%>
									${resultC}${resultD}
								상태입니다.
							</td>
						</c:if>
						<td></td>
						<td align="left">
							<c:if test="${! empty purchase.tranCode}">
								<c:if test="${purchase.tranCode.trim() == 'c'}">
							<span class="clickableSpan" data-update data-prodNo="${purchase.purchaseProd.prodNo}">
									물건도착
									</span>
								</c:if>
							</c:if>

						</td>
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center"><input type="hidden" id="currentPage"
						name="currentPage" value="1" /> <jsp:include
							page="../common/pageNavigator.jsp" /></td>

				</tr>
			</table>
			<!--  페이지 Navigator 끝 -->
		</form>

	</div>

</body>
</html>