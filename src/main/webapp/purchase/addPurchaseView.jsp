<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>


    <title>Insert title here</title>
    <%--제이쿼리--%>
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script
            src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"
            integrity="sha256-6XMVI0zB8cRzfZjqKcD01PBsAy3FlDASrlC8SxCpInY="
            crossorigin="anonymous"></script>


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
            padding-top: 80px;
        }

        #ui-datepicker-div {
            display: none;
        }

        img.ui-datepicker-trigger {
            display: none;
        }
        .font-size{
            font-size: 16px;
        }
        .mb-4{
            margin-bottom:20px;
        }

	</style>
    <script type="text/javascript">
        $(document).ready(function () {
            function fncAddPurchase() {
                //Form 유효성 검증

                var receiverName = $("form[name='detailForm'] input[name='receiverName']").val();
                var receiverPhone = $("form[name='detailForm'] input[name='receiverPhone']").val();
                var divyAddr = $("form[name='detailForm'] input[name='divyAddr']").val();
                var divyRequest = $("form[name='detailForm'] input[name='divyRequest']").val();
                var divyDate = $("form[name='detailForm'] input[name='divyDate']").val();
                var typeCount = $("form[name='detailForm'] input[name='typeCount']").val();

                if (receiverName == null || receiverName.length < 1) {
                    alert("구매자이름은 반드시 입력하여야 합니다.");
                    return;
                }
                if (receiverPhone == null || receiverPhone.length < 1) {
                    alert("구매자연락처는 반드시 입력하여야 합니다.");
                    return;
                }
                if (divyAddr == null || divyAddr.length < 1) {
                    alert("구매자주소는 반드시 입력하셔야 합니다.");
                    return;
                }
                if (divyRequest == null || divyRequest.length < 1) {
                    alert("구매요청사항은 반드시 입력하셔야 합니다.");
                    return;
                }
                if (divyDate == null || divyDate.length < 1) {
                    alert("배송희망일자는 반드시 입력하셔야 합니다.");
                    return;
                }
                if (divyDate == null || divyDate.length < 1) {
                    alert("배송희망일자는 반드시 입력하셔야 합니다.");
                    return;
                }
                if (typeCount == null || typeCount.length < 1) {
                    alert("구매수량은 반드시 입력하셔야 합니다.");
                    return;
                }

                $("form[name='detailForm']")
                    .attr("method", "post")
                    .attr("action", "/purchase/addPurchase")
                    .submit();
                    //.trigger("submit");

            }//end of fncAddPurchase


            $("[data-calendar]").click(function () {
                console.log('안녕');
                show_calendar('document.detailForm.divyDate', $("form[name='detailForm'] input[name='divyDate']").val());
            });
            $("button[data-addPurchase]").click(function () {
                fncAddPurchase();
            });
            $("button[data-cancel]").click(function () {
                history.go(-1);
            });
        });
        /* function resetData(){
            document.detailForm.reset(); method="post" action="/purchase/addPurchase"
        } */
    </script>
</head>

<body class="default-font">
<jsp:include page="/layout/toolbar.jsp"/>
<div class="container">
    <form class="form-horizontal default-font" name="detailForm">
        <h1 class="bg-primary text-center" style="border-radius: 10px">상품 구매</h1>

        <div class="row mb-4">
            <div class="col-sm-4"></div>
            <div class="col-sm-5 font-size">
           제품들이 모두 구매됩니다.
            </div>
        </div>

        <c:forEach var="product" items="${productList}">
        <div class="row col-sm-12 mb-4">
            <label for="prodNo" class="col-sm-2 col-form-label font-size">상품번호</label>
                <div class="col-sm-2">
                <select name="prodNo" class="form-control" id="prodNo" readonly>
                        <option value="${product.prodNo}">${product.prodNo}</option>
                </select>
                </div>
            <label for="prodName" class="col-sm-2 col-form-label font-size">상품명</label>
                <div class="col-sm-2">
            <select name="prodName" class="form-control" id="prodName" readonly>
                <option value="${product.prodName}">${product.prodName}</option>
            </select>
                </div>
            <label for="prodDetail" class="col-sm-2 col-form-label font-size">상품상세정보</label>
            <div class="col-sm-2">
                <select name="prodDetail" class="form-control" id="prodDetail" readonly>
                    <option value="${product.prodDetail}">${product.prodDetail}</option>
                </select>
            </div>
            <label for="manuDate" class="col-sm-2 col-form-label font-size">제조일자</label>
            <div class="col-sm-2">
                <select name="manuDate" class="form-control" id="manuDate" readonly>
                    <option value="${product.manuDate}">${product.manuDate}</option>
                </select>
            </div>
            <label for="price" class="col-sm-2 col-form-label font-size">제조일자</label>
            <div class="col-sm-2">
                <select name="price" class="form-control" id="price" readonly>
                    <option value="${product.price}">${product.price}</option>
                </select>
            </div>
            <label for="stockQuantity" class="col-sm-2 col-form-label font-size">재고</label>
            <div class="col-sm-2">
                <select name="stockQuantity" class="form-control" id="stockQuantity" readonly>
                    <option value="${product.stockQuantity}">${product.stockQuantity}</option>
                </select>
            </div>
            <label for="regDate" class="col-sm-2 col-form-label font-size">등록일자</label>
            <div class="col-sm-2">
                <select name="regDate" class="form-control" id="regDate" readonly>
                    <option value="${product.regDate}">${product.regDate}</option>
                </select>
            </div>
        </div>
        </c:forEach>
        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="userId" class="offset-sm-2 col-sm-2 col-form-label font-size">구매자아이디</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="userId" name="userId" value="${user.userId}" readonly>
            </div>
        </div>
        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="paymentOption" class="offset-sm-2 col-sm-2 col-form-label font-size">구매방법</label>
            <div class="col-sm-5">
                <select name="paymentOption" class="form-control" id="paymentOption">
                    <option value="1" selected="selected">현금구매</option>
                    <option value="2">신용구매</option>
                </select>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="receiverName" class="offset-sm-2 col-sm-2 col-form-label font-size">구매자이름</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="receiverName" name="receiverName" value="${user.userName}"
                >
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="receiverPhone" class="offset-sm-2 col-sm-2 col-form-label font-size">구매자연락처</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="receiverPhone" name="receiverPhone" value="${user.phone}"
                >
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="divyAddr" class="offset-sm-2 col-sm-2 col-form-label font-size">구매자주소</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="divyAddr" name="divyAddr" value="${user.addr}">
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="divyRequest" class="offset-sm-2 col-sm-2 col-form-label font-size"></label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="divyRequest" name="divyRequest" value="">
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-sm-3"></div>
            <label for="datepicker" class="offset-sm-2 col-sm-2 col-form-label font-size">배송희망일자</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="datepicker" name="divyDate">
            </div>
        </div>

        <div class=""
        <c:forEach var="product" items="${productList}">
        <div class="row col-sm-1">
            <label for="typeCount" class="col-form-label font-size">구매수량</label>
        </div>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="typeCount" name="typeCount">
            </div>
        </c:forEach>
        <div class="row mb-9">
            <div class="col-sm-3"></div>
            <div class="offset-sm-4 col-sm-4 pull-right"></div>
            <div class="offset-sm-8 col-sm-8 text-right pull-right">
                <button type="button" class="btn btn-primary" data-addPurchase>
                    구매
                </button>
                <button type="button" class="btn btn-primary" data-cancel>
                    취소
                </button>
            </div>
        </div>



    </form>
</div>


<script>
	$(function() {
		//input을 datepicker로 선언
		$("#datepicker").datepicker({
			dateFormat: 'yy-mm-dd' //달력 날짜 형태
			,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
			,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
			,changeYear: true //option값 년 선택 가능
			,changeMonth: true //option값  월 선택 가능
			,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시
			,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
			,buttonImageOnly: true //버튼 이미지만 깔끔하게 보이게함
			,buttonText: "선택" //버튼 호버 텍스트
			,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
			,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
			,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
			,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
			,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
			,minDate: "-5Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
			,maxDate: "+5y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
		});

		//초기값을 오늘 날짜로 설정해줘야 합니다.
		$('#datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)

	});


</script>
</body>
</html>