<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 상품 상세 조회-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%--
<%
Product productVO=(Product)request.getAttribute("productVO");
	session.setAttribute("purchaseProd", productVO);
	//System.out.println(request.getParameter("menu"));
%>
--%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <%--    부트스트랩--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%-- 부트스트랩 Dropdown Hover CSS JS--%>
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <script src="/js/bootstrap-dropdownhover.min.js"></script>

    <%--    사용자--%>
    <link rel="stylesheet" href="/css/font.css" type="text/css">
    <style>
        body {
            padding-top: 50px;
        }

        .max-size {
            max-width: 100px !important;
            max-height: 100px !important;
        }
    </style>
    <%--    <title>상품상세조회</title>--%>
</head>

<body>

<!-- ToolBar Start /////////////////////////////////////-->
<jsp:include page="/layout/toolbar.jsp"/>
<!-- ToolBar End /////////////////////////////////////-->
<%-- method="post" enctype="multipart/form-data"--%>
<div class="container default-font">

    <div class="page-header">
        <h3 class=" text-info">상품상세조회</h3>
        <h5 class="text-muted">상품 정보를 <strong class="text-danger">최신정보로 관리</strong>해 주세요.</h5>
    </div>

    <div class="row">
        <div class="col-xs-4 col-md-2"><strong>상품번호</strong></div>
        <div class="col-xs-8 col-md-4">${product.prodNo}</div>
    </div>

    <hr/>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>상품명</strong></div>
        <div class="col-xs-8 col-md-4">${product.prodName}</div>
    </div>

    <hr/>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>상품이미지</strong></div>
        <c:forEach var="fileName" items="${fileNameList}">
            <div class="col-xs-8 col-md-4">
                <img class="max-size" src="${pageContext.request.contextPath}/images/uploadFiles/${fileName}"/>
            </div>
        </c:forEach>
        ${product.fileName}
    </div>

    <hr/>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>상품상세정보</strong></div>
        <div class="col-xs-8 col-md-4">${product.prodDetail}</div>
    </div>

    <hr/>

    <div class="row">
        <div class="col-xs-4 col-md-2"><strong>제조일자</strong></div>
        <div class="col-xs-8 col-md-4">${product.manuDate}</div>
    </div>

    <hr/>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>가격</strong></div>
        <div class="col-xs-8 col-md-4">${product.price}</div>
    </div>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>등록일자</strong></div>
        <div class="col-xs-8 col-md-4">${product.regDate}</div>
    </div>

    <div class="row">
        <div class="col-xs-4 col-md-2 "><strong>수량</strong></div>
        <div class="col-xs-8 col-md-4">${product.stockQuantity}</div>
    </div>

    <hr/>


    <div class="row">
    <c:if test="${menu}!=null">
        <c:if test="${menu}=='ok'">

            <div class="col-sm-offset-4  col-sm-4 text-center ">
                <button type="button" class="btn btn-primary confirm">확인</button>
            </div>

        </c:if>
        <c:if test="${menu}!='ok'">
            <div class="col-sm-offset-4  col-sm-4 text-center ">
                <button type="button" class="btn btn-primary buy">구매</button>
            </div>
            <div class="col-sm-offset-4  col-sm-4 text-center ">
                <button type="button" class="btn btn-primary back">이전</button>
            </div>
        </c:if>
        </c:if>
        <c:if test="${menu}==null">
            <div class="col-sm-offset-4  col-sm-4 text-center ">
                <button type="button" class="btn btn-primary confirm">확인</button>
            </div>
        </c:if>
    </div>


    <div class="row">
                <div class="col-sm-offset-8  col-sm-4 text-center ">
                    <button type="button" class="btn btn-primary buy">구매하기</button>
                    <button type="button" class="btn btn-primary confirm">상품목록으로..</button>
                    <button type="button" class="btn btn-primary back">이전</button>
                </div>
    </div>

    <br/>
</div>


<%--<div class="col-sm-offset-4  col-sm-4 text-center">--%>
<%--    <button type="button" class="btn btn-primary add">등&nbsp;록</button>--%>
<%--    <button type="button" class="btn btn-primary cancel">취&nbsp;소</button>--%>
<%--    <button type="button" class="btn btn-primary back">뒤로가기</button>--%>
<%--</div>--%>
<script type="text/javascript">

    $(document).ready(function () {
        $("button.confirm").bind('click', function () {
            self.location = "/product/listProduct?menu=manage";
        });
        $("button.buy").bind('click', function () {
            self.location = "/purchase/addPurchase?prodNo=${product.prodNo}";
        })
        $("button.back").bind('click', function () {
            history.go(-1);
        })

        //어드민계정으로 구매 X
        if('${user.role}'=='admin'){
            $("button.buy").prop('disabled', true);
        }

    });//end of ready
</script>
</body>

</html>