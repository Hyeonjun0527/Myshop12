
<%@page import="org.apache.jasper.tagplugins.jstl.core.Param"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
<title>찜 리스트</title>


<link href="/css/listProduct.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body bgcolor="#ffffff" text="#000000">
<script>


	let type = '${search.searchType}';//없으면 정말 아무것도 없는 공백이 됨.''가 됨
	let searchBoundFirst = '${search.searchBoundFirst}';//'0'이 됨
	let searchBoundEnd = '${search.searchBoundEnd}';
	console.log('jsp에서 type', type);
	console.log('jsp에서 searchBoundFirst',searchBoundFirst);
	console.log('jsp에서 searchBoundEnd',searchBoundEnd);

	$(document).ready(function(){

		const td = $("td[data-getProduct]")
		console.log("td :: " + td);
		const prodNo = td.data("getproduct")
		console.log("prodNO :: "+prodNo);
		console.log("menu :: " + "${param.menu}");

		$("div.delete_one:contains('삭제')").click(function(){
			console.log("1이다 이야");
			self.location = "/cookie/removeLike?count=one&prodNo="+prodNo;
		});
		$("div.delete_all:contains('삭제')").click(function(){
			console.log("2이다 이야");
			window.location.href = "/cookie/removeLike?count=all&prodNo="+prodNo;
		});
		td.click(function(){
			console.log("3이다 이야");
			window.location.href = "/product/getProduct?prodNo="+prodNo+"&menu=${param.menu}";
		});

		$("span.update").click(function(){
			console.log("4이다 이야");
			window.location.href = "/purchase/updateTranCode?prodNo="+prodNo+"&navigationPage=listProduct&menu=manage";
		});

	});

	</script>
	<div style="width: 98%; margin-left: 10px;">
<%--이 폼태그를 전달하는 건 1,2,3,4클릭이나 검색할때만임. --%>
		<form name="detailForm">
			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">
								찜 리스트
								</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>
<div class="container">
</div>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${products.size()} 건수
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">남은수량</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>
					<td class="ct_line02"></td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				
				<c:set var="i" value="0" />
				<c:forEach var="product" items="${products}">
				
					<c:set var="i" value="${i+1 }"/>
					<tr class="ct_list_pop">
								<td align="center">		<%--<button style="border: 0px; padding: 0px;">--%>
		<div style="text-align: center;" class="delete_one">
				삭제
		</div>
	${i}</td>
								<td></td>
								<c:if test="${!(product.proTranCode=='a')}">
									<td align="left" data-getProduct="${product.prodNo}">${product.prodName}</td>
								</c:if>
								<c:if test="${product.proTranCode=='a'}">
									<td align="left">${product.prodName}</td>
								</c:if>
								<td></td>
								<td align="left">${product.price }</td>
								<!-- 가격 -->
								<td></td>
								<td align="left">${product.stockQuantity}</td>
								<!-- 가격 -->
								<td></td>
								<td align="left">${product.regDate}</td>
								<td></td>
								<c:if test="${product.proTranCode!=null}">

										<c:set var="resultA" value="${product.proTranCode.trim() == 'a' ? '판매중' : ''}"/>

										<c:set var="resultB" value="${product.proTranCode.trim() == 'b' ? '구매완료' : ''}"/>
										<c:set var="resultB2" value=""/>
							<%--
										<c:if test="${param.menu == 'manage' || param.menu == 'ok'}">

										    <c:set var="resultB2" value="${product.proTranCode.trim() == 'b' ? '배송하기' : ''}"/>
										</c:if>
						--%>
										<c:set var="resultC" value="${product.proTranCode.trim() == 'c' ? '배송중' : ''}"/>
										<c:set var="resultD" value="${product.proTranCode.trim() == 'd' ? '배송완료' : ''}"/>
										
								<td align="left">${resultA}${resultB}${(!empty resultB) ? '&nbsp;&nbsp;' : ''}
									<%--<span class="update">${resultB2}</span>--%>
										${resultC}${resultD}</td>
								</c:if>
						<tr/>
						<tr>
								<td colspan="11" bgcolor="D6D7D6" height="1"></td>
						</tr>
						
				</c:forEach>
				
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
			</table>


		</form>

	</div>
		<button style="border: 0px; padding: 0px;">
		<div style="text-align: center;" class="delete_all">
			삭제
		</div>
	</button>
	<script type="text/javascript"  src="/js/variousSearch.js"></script>
<a href="/main.jsp">메인으로 이동하기</a>
</body>
</html>