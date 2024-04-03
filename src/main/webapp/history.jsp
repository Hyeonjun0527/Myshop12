<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--히스토리 있으면 반복하는데 , 링크 반복해서 만드는데 히스토리=prodNo --%>
<html>
<head>
<title>열어본 상품 보기</title>
</head>
<link rel="stylesheet" href="/css/font.css" type="text/css">
<body class="default-font">
	최근 본 상품 목록
	<br>
	<br>

	<c:if test="${!empty products}">
		<c:forEach var="product" items="${products}">
			<c:if test="${!empty product}">
				<a href="/product/getProduct?prodNo=${product.prodNo}&menu=search"
					target="rightFrame">${product.prodName}</a>
				<br />
			</c:if>
		</c:forEach>
	</c:if>


	<button style="border: 0px; padding: 0px;">
		<div style="text-align: center;">
			<a href="/cookie/removeHistory?navigationPage=history.jsp"
				style="display: inline-block; background-color: #4CAF50; color: white; padding: 7px 10px; text-align: center; text-decoration: none; font-size: 16px; border-radius: 5px; cursor: pointer;">히스토리 초기화</a>
		</div>
	</button>

</body>
</html>