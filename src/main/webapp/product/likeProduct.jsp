<%@page import="org.apache.jasper.tagplugins.jstl.core.Param" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>찜 리스트</title>
    <%--제이쿼리--%>
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>

    <%--부트스트랩--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%-- 부트스트랩 Dropdown Hover CSS JS--%>
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <script src="/js/bootstrap-dropdownhover.min.js"></script>
    <script
            src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"
            integrity="sha256-6XMVI0zB8cRzfZjqKcD01PBsAy3FlDASrlC8SxCpInY="
            crossorigin="anonymous"></script>
    <%--사용자--%>
    <link rel="stylesheet" href="/css/font.css" type="text/css">

    <link href="/css/listProduct.css" rel="stylesheet" type="text/css">
    <style>
        body {
            padding-top: 50px;
        }

        .size-set {
            width: 1140px;
            margin-left: 15px;
        }

        .max-size {
            max-width: 100px !important;
            max-height: 100px !important;
        }

        .no-padding {
            padding: 0;
        }
    </style>
</head>

<body class="default-font">
<jsp:include page="/layout/toolbar.jsp"/>
<script>


    let type = '${search.searchType}';//없으면 정말 아무것도 없는 공백이 됨.''가 됨
    let searchBoundFirst = '${search.searchBoundFirst}';//'0'이 됨
    let searchBoundEnd = '${search.searchBoundEnd}';
    console.log('jsp에서 type', type);
    console.log('jsp에서 searchBoundFirst', searchBoundFirst);
    console.log('jsp에서 searchBoundEnd', searchBoundEnd);

    $(document).ready(function () {

        const td = $("td[data-getProduct]")
        console.log("td :: " + td);
        const prodNo = td.data("getproduct")
        console.log("prodNO :: " + prodNo);
        console.log("menu :: " + "${param.menu}");

        $("div.delete_one:contains('삭제')").click(function () {
            console.log("1이다 이야");
            self.location = "/cookie/removeLike?count=one&prodNo=" + prodNo;
        });
        $("div.delete_all:contains('삭제')").click(function () {
            console.log("2이다 이야");
            window.location.href = "/cookie/removeLike?count=all&prodNo=" + prodNo;
        });
        td.click(function () {
            console.log("3이다 이야");
            window.location.href = "/product/getProduct?prodNo=" + prodNo + "&menu=${param.menu}";
        });

        $("span.update").click(function () {
            console.log("4이다 이야");
            window.location.href = "/purchase/updateTranCode?prodNo=" + prodNo + "&navigationPage=listProduct&menu=manage";
        });

    });

</script>
<div class="container">
    <div class="page-header text-info">
        <h3>찜 리스트</h3>
    </div>

    <div class="row">
        <%--이 폼태그를 전달하는 건 1,2,3,4클릭이나 검색할때만임. --%>
        <div class="col-md-6 col-sm-6 text-left">
            <p class="text-primary">
                전체 ${products.size()} 건수
            </p>
        </div>
    </div>
    <%--이 폼태그를 전달하는 건 1,2,3,4클릭이나 검색할때만임. --%>
    <form class="form-inline" name="detailForm">

        <table class="table table-hover table-striped size-set">
            <thead>
            <tr>
                <td>No</td>
                <td>상품명</td>
                <td>가격</td>
                <td>남은수량</td>
                <td>등록일</td>
                <td>현재상태</td>
            </tr>
            </thead>

            <c:if test="${products!=null}">
                <c:set var="i" value="0"/>
                <c:forEach var="product" items="${products}">

                    <c:set var="i" value="${i+1 }"/>
                    <tr class="ct_list_pop">
                        <td align="left">
                                ${i}&emsp;&emsp;
                            <button class="btn btn-primary">
                                <div style="text-align: center;" class="delete_one">
                                    삭제
                                </div>
                            </button>
                        </td>

                        <c:if test="${!(product.proTranCode=='a')}">
                        <td align="left" data-getProduct="${product.prodNo}">${product.prodName}</td>
                        </c:if>
                        <c:if test="${product.proTranCode=='a'}">
                        <td align="left">${product.prodName}</td>
                        </c:if>
                        <td align="left">${product.price }</td>
                        <td align="left">${product.stockQuantity}</td>
                        <td align="left">${product.regDate}</td>
                        <c:if test="${product.proTranCode!=null}">
                            <c:set var="resultA" value="${product.proTranCode.trim() == 'a' ? '구매가능' : ''}"/>
                            <c:set var="resultB" value="${product.proTranCode.trim() == 'b' ? '구매불가' : ''}"/>
                        <td align="left">${resultA}${resultB}${(!empty resultB) ? '&nbsp;&nbsp;' : ''}
                                <%--<span class="update">${resultB2}</span>--%>
                            </c:if>
                    <tr/>

                </c:forEach>
            </c:if>
        </table>

    </form>

</div>
<div class="col-md-9 col-sm-9">
</div>
<div class="col-md-3 col-sm-3 text-left">
    <button class="btn btn-primary">
        <div style="text-align: center;" class="delete_all">
            전부 삭제하기
        </div>
    </button>
</div>
<div class="col-md-12 col-sm-12">
    &nbsp;
</div>
<div class="col-md-9 col-sm-9">
</div>
<c:if test="${products!=null}">
    <c:forEach var="product" items="${products}">
        <c:set var="prodNoListString" value="${prodNoListString}prodNo=${product.prodNo}&"/>
    </c:forEach>
    <c:set var="prodNoListString" value="${prodNoListString.substring(0,prodNoListString.length() - 1)}"/>
</c:if>

<c:if test="${pass!=false}">
    <div class="col-md-3 col-sm-3">
        <a href="/purchase/addPurchase?${prodNoListString}" class="btn btn-primary">모두 구매하기</a>
    </div>
</c:if>
<c:if test="${pass==false}">
    <div class="col-md-3 col-sm-3">
        <a href="#" class="btn btn-primary">재고가 없으면 구매할 수 없습니다.</a>
    </div>
</c:if>
<script type="text/javascript" src="/js/variousSearch.js"></script>
<script>
</script>
</body>
</html>