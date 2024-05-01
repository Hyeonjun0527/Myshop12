<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 메뉴랑 프로덕트 menu product --%>
<html>
<head>
    <title>상품정보수정</title>
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

    <script type="text/javascript">
        $(document).ready(function () {



            function fncUpdateProduct() {
                console.log("Here");

                // //Form 유효성 검증
                // var name = document.detailForm.prodName.value;
                // var detail = document.detailForm.prodDetail.value;
                // var manuDate = document.detailForm.manuDate.value;
                // var price = document.detailForm.price.value;
                var name = $("form[name='detailForm'] input[name='prodName']").val();
                var detail = $("form[name='detailForm'] input[name='prodDetail']").val();
                var manuDate = $("form[name='detailForm'] input[name='manuDate']").val();
                var price = $("form[name='detailForm'] input[name='price']").val();
                if (name == null || name.length < 1) {
                    alert("상품명은 반드시 입력하여야 합니다.");
                    return;
                }
                if (detail == null || detail.length < 1) {
                    alert("상품상세정보는 반드시 입력하여야 합니다.");
                    return;
                }
                if (manuDate == null || manuDate.length < 1) {
                    alert("제조일자는 반드시 입력하셔야 합니다.");
                    return;
                }
                if (price == null || price.length < 1) {
                    alert("가격은 반드시 입력하셔야 합니다.");
                    return;
                }
                const url = '/product/updateProduct';
                document.detailForm.submit();
                $("form[name='detailForm']")
						.attr("method", "POST")
						.attr("action", url)
						.attr("enctype","multipart/form-data")
						.submit();
                <%--여기에 ${requestScope.product.prodNo} 마렵지? 어케해? form태그 안에 인풋하나 히든으로 만들어서 파라미터 보내라--%>
            }//end of fncUpdateProduct()

            $("img[data-calendar]").click(function () {
                show_calendar('document.detailForm.manuDate', $("form[name='detailForm']input[name='manuDate']").val());
            });
            $("button[data-update]").click(function () {
                fncUpdateProduct();
            });
            $("button[data-cancel]").click(function () {
                history.go(-1);
            });
        });
    </script>
</head>

<body>
<div class="container default-font">
    <form name="detailForm">
        <input type="hidden" name="prodNo" value="${requestScope.product.prodNo}">

        <div class="page-header">
            <h3 class="text-info">상품수정</h3>
        </div>

        <div class="row">
            <div class="col-xs-4 col-md-2"><strong>상품명</strong></div>
            <div class="col-xs-8 col-md-4">
                <input type="text" name="prodName" class="form-control" value="${requestScope.product.prodName}" maxlength="20">
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-xs-4 col-md-2"><strong>상품상세정보</strong></div>
            <div class="col-xs-8 col-md-4">
                <input type="text" name="prodDetail" class="form-control" value="${requestScope.product.prodDetail}" maxlength="50">
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-xs-4 col-md-2"><strong>제조일자</strong></div>
            <div class="col-xs-8 col-md-4">
                <input type="text" name="manuDate" class="form-control" value="${requestScope.product.manuDate}" readonly>
                <img src="../images/ct_icon_date.gif" width="15" height="15" data-calendar/>
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-xs-4 col-md-2"><strong>가격</strong></div>
            <div class="col-xs-8 col-md-4">
                <input type="text" name="price" class="form-control" value="${requestScope.product.price}" maxlength="10">&nbsp;원
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-xs-4 col-md-2"><strong>상품이미지</strong></div>
            <div class="col-xs-8 col-md-4">
                <input	type="file" name="fileList" multiple class="form-control" id="fileList"/>
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-sm-offset-4 col-sm-4 text-center">
                <button type="button" class="btn btn-primary" data-update>수정</button>
                <button type="button" class="btn btn-default" data-cancel>취소</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>