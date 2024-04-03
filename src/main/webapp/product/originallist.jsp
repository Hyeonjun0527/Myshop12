<!-- 상품목록조회 -->
<%@page import="org.apache.jasper.tagplugins.jstl.core.Param" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>상품 목록조회</title>
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
    <%--사용자--%>
    <script
            src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"
            integrity="sha256-6XMVI0zB8cRzfZjqKcD01PBsAy3FlDASrlC8SxCpInY="
            crossorigin="anonymous"></script>
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
    <link href="/css/listProduct.css" rel="stylesheet" type="text/css">

</head>
<body class="default-font">
<script>

    let type = '${search.searchType}';//없으면 정말 아무것도 없는 공백이 됨.''가 됨
    let searchBoundFirst = '${search.searchBoundFirst}';//'0'이 됨
    let searchBoundEnd = '${search.searchBoundEnd}';

    let menu = '${menu}';
    console.log(menu);
    console.log('jsp에서 searchBoundFirst', searchBoundFirst);
    console.log('jsp에서 searchBoundEnd', searchBoundEnd);
    console.log('jsp에서 type', type);

</script>

<div style="width: 98%; margin-left: 10px;">
    <%--이 폼태그를 전달하는 건 1,2,3,4클릭이나 검색할때만임. --%>
    <form name="detailForm">

        <table width="100%" height="37" border="0" cellpadding="0"
               cellspacing="0">
            <tr>
                <td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
                                                width="15" height="37"/></td>
                <td background="/images/ct_ttl_img02.gif" width="100%"
                    style="padding-left: 10px;">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="93%" class="ct_ttl01">
                                ${menu=='manage' ? '상품관리' :'상품목록조회'}
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
                                                width="12" height="37"/></td>
            </tr>
        </table>


        <table width="100%" border="0" cellspacing="0" cellpadding="0"
               style="margin-top: 10px;">
            <tr>
                <!-- searchCondition의 value 0 1 2 -->
                <td align="right"><select name="searchCondition"
                                          class="ct_input_g" style="width: 80px">

                    <c:choose>
                        <c:when test="${requestScope.search.searchCondition=='0'}">
                            <option value="0" selected>상품번호</option>
                            <option value="1">상품명</option>
                            <option value="2">상품가격</option>
                        </c:when>
                        <c:when test="${requestScope.search.searchCondition=='1'}">
                            <option value="0">상품번호</option>
                            <option value="1" selected>상품명</option>
                            <option value="2">상품가격</option>
                        </c:when>
                        <c:when test="${requestScope.search.searchCondition=='2'}">
                            <option value="0">상품번호</option>
                            <option value="1">상품명</option>
                            <option value="2" selected>상품가격</option>
                        </c:when>
                        <c:otherwise>
                            <option value="0">상품번호</option>
                            <option value="1">상품명</option>
                            <option value="2">상품가격</option>
                        </c:otherwise>
                    </c:choose>

                </select> <input type="text" name="searchKeyword" value="${search.searchKeyword}" class="ct_input_g"
                                 style="width: 200px; height: 19px"/></td>

                <td align="right" width="70">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="ct_btn01" data-search
                                style="padding-top: 1px;"><button class="button keyword" type="button">검색</button></td>
                            <%--여기가 1인 이유는 검색했을때 페이지가 1이기 때문이다. --%>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <div class="container">

            <input type="radio" id="searchType1" name="searchType" value="1"/>
            <label for="searchType1" class="button">일반 검색</label>

            <input type="radio" id="searchType2" name="searchType" value="2"/>
            <label for="searchType2" class="button">가격 높은순 검색</label>

            <input type="radio" id="searchType3" name="searchType" value="3"/>
            <label for="searchType3" class="button">가격 낮은순 검색</label>

            <input type="text" id="searchBoundFirst" name="searchBoundFirst" value='${search.searchBoundFirst}'/>
            부터

            <input type="text" id="searchBoundEnd" name="searchBoundEnd" value='${search.searchBoundEnd}'/>
            까지

            <button type="button" data-searchBound>검색</button>

        </div>

        <table width="100%" border="0" cellspacing="0" cellpadding="0"
               style="margin-top: 10px;">
            <tr>
                <td colspan="11">전체 ${totalCount} 건수, 현재 ${requestScope.resultPage.currentPage} 페이지
                </td>
            </tr>
            <tr>
                <td class="ct_list_b" width="100">No</td>
                <td class="ct_line02"></td>
                <td class="ct_list_b" width="150">상품명</td>
                <td class="ct_line02"></td>
                <td class="ct_list_b" width="150">가격</td>
                <td class="ct_line02"></td>
                <td class="ct_list_b">등록일</td>
                <td class="ct_line02"></td>
                <td class="ct_list_b">현재상태</td>
                <td class="ct_line02"></td>
                <td class="ct_list_b" width="150">찜하기</td>
            </tr>
            <tr>
                <td colspan="11" bgcolor="808285" height="1"></td>
            </tr>

            <c:set var="i" value="0"/>
            <c:forEach var="product" items="${list}">
                <c:set var="i" value="${i+1 }"/>
                <tr class="ct_list_pop">
                    <td align="center">${i}</td>
                    <td></td>
                    <c:if test="${!(product.proTranCode=='a')}">
                    <td align="left">${product.prodName}</td>
                    </c:if>
                    <c:if test="${product.proTranCode=='a'}">
                    <td align="left">
                        <button type="button" data-getProduct data-prodNo="${product.prodNo}">
                                ${product.prodName}
                        </button>
                    </td>
                    </c:if>
                    <td></td>
                    <td align="left">${product.price}</td>
                    <!-- 가격 -->
                    <td></td>
                    <td align="left">${product.regDate}</td>
                    <td></td>
                    <c:if test="${product.proTranCode!=null}">

                        <c:set var="resultA" value="${product.proTranCode.trim() == 'a' ? '판매중' : ''}"/>

                        <c:set var="resultB" value="${product.proTranCode.trim() == 'b' ? '판매완료' : ''}"/>
                        <c:set var="resultB2" value=""/>
                    <c:if test="${menu == 'manage' || menu == 'ok'}">
                        <c:set var="resultB2" value="${product.proTranCode.trim() == 'b' ? '배송하기' : ''}"/>
                    </c:if>
                        <c:set var="resultC" value="${product.proTranCode.trim() == 'c' ? '배송중' : ''}"/>
                        <c:set var="resultD" value="${product.proTranCode.trim() == 'd' ? '배송완료' : ''}"/>

                    <td align="left">${resultA}${resultB}${(!empty resultB) ? '&nbsp;&nbsp;' : ''}
                        <span class="clickableSpan" data-update
                              data-prodNo="${product.prodNo}">${resultB2}</span>${resultC}${resultD}
                    </td>
                    </c:if>
                    <td></td>
                    <td>
                        <button type="button" data-setLike data-prodNo="${product.prodNo}">
                            찜하기
                        </button>
                    </td>
                <tr/>
                <tr>
                    <td colspan="11" bgcolor="D6D7D6" height="1"></td>
                </tr>
            </c:forEach>

        </table>

        <table width="100%" border="0" cellspacing="0" cellpadding="0"
               style="margin-top: 10px;">
            <tr>
                <td align="center">

                    <input type="hidden" id="currentPage" name="currentPage" value=""/>

                    <jsp:include page="${pageContext.request.contextPath}/common/pageNavigator.jsp"/>

                </td>

            </tr>
        </table>
        <!--  페이지 Navigator 끝 -->
    </form>

</div>



<script type="text/javascript" src="${pageContext.request.contextPath}/js/variousSearch.js"></script>
<script>
    $(document).ready(function () {
        $('td.ct_btn01[data-search] button:contains("검색")').click(function () {
            fncGetList('1');
        });
        $('button[data-searchBound]:contains("검색")').click(function () {
            fncGetList('${resultPage.currentPage}');
        });
        $('button[data-getProduct]').click(function () {
            let prodNo = $(this).data('prodno');
            window.location.href = "/product/getProduct?prodNo=" + prodNo + "&menu=${menu}";
        });
        $('button[data-update]').click(function () {
            let prodNo = $(this).data('prodno');
            window.location.href = "/purchase/updateTranCode?prodNo=" + prodNo + "&navigationPage=listProduct&menu=manage";
        })
        $('button[data-setLike]').click(function () {
            let prodNo = $(this).data('prodno');
            window.location.href = "/product/setLikeProduct?prodNo=" + prodNo + "&menu=${menu}&currentPage=${resultPage.currentPage}";
        })
        $('input[name=searchType]').click(function () {
            fncGetList('1');
        })

        $('input[name="searchKeyword"]').autocomplete({
            source: function (request, response) {

                function createLabelValueMapper(Prop){
                    return function(object){
                        return {
                            label:object[Prop],
                            value:object[Prop]
                        }
                    }
                };
                let searchCondition = $('select[name=searchCondition]').val()

                console.log("소스가 호출됩니다.");
                console.log("줄 데이터 : request.term : " + request.term);

                console.log("줄 데이터 : Condition : " + searchCondition);
                console.log(JSON.stringify({
                    searchKeyword : request.term,
                    searchCondition : searchCondition
                }));
                $.ajax({
                    url: '/product/json/autoComplete',
                    type: 'post',
                    dataType: 'json',
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify({
                        searchKeyword : request.term,
                        searchCondition : searchCondition
                    }),
                }).done(function (rawData, textStatus, jqXHR) {
                    console.log('받은 데이터 : '  + rawData );
                    if (rawData.length !== 0) {
                        // 서버로부터 받은 데이터 (Product 객체의 리스트)를 처리
                        console.log('데이터가 존재 data : ' + rawData);
                        //번호, 명, 가격 $.map은 요소에 function 다 적용시키고 배열 반환
                        //그 function을 만드는 function을 만들면 더 편하겠다. 그게 mapper변수

                        switch(searchCondition) {
                            case '0':
                                mapper = createLabelValueMapper("prodNo");
                                break;
                            case "1":
                                mapper = createLabelValueMapper("prodName");
                                break;
                            case "2":
                                mapper = createLabelValueMapper("price");
                                break;
                        }

                        let data = $.map(rawData, mapper);
                        console.log('data : ' + data);
                        $.each(data,function(index,obj){
                            console.log(obj);
                        })
                        console.log("data[0] : " + JSON.stringify(data[0]));
                        let matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex(request.term), "i" );
                        console.log($.grep( data, function(item){
                            return matcher.test(item.label);
                        }) );
                        response( $.grep( data, function(item){
                            return matcher.test(item.label);
                        }).slice(0,5));
                        //.slice(0,7)
                        //response(data); // 처리된 데이터를 자동완성 목록으로 표시
                    } else {
                        // 서버로부터 받은 데이터가 없을 때는 빈 배열을 전달하여 자동완성을 제안하지 않음
                        response([]);
                    }

                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log('실패');
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                });
            },//end of source
            select: function(event, ui) {
                // 옵션을 선택했을 때, 원하는 동작을 정의할 수 있음
                // 예를 들어, 선택된 Product의 id를 어딘가에 저장하거나 다른 작업을 수행
                console.log("Selected: " + ui.item.label + ", id: " + ui.item.value);
                let $element = $('input[name="searchKeyword"]');

                $element.addClass('flash-effect');
                $element.one('animationed',function(){
                    $element.removeClass('flash-effect');
                });
            },//end of select event
            classes: {
                "ui-autocomplete": "custom-ui-autocomplete",
            },

            autoFocus: true,//end of autoFocus

        });//end of autocomplete

        // $.ui.autocomplete.prototype._close = function(event) {
        //     // 닫힘 동작을 무시하거나 조건에 따라 처리
        // };
    });//end of ready
</script>

</body>
</html>