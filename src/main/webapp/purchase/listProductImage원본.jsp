
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

        .thumbnail .imgWrapper {
            display: inline-block;
        }

        .thumbnail .imgWrapper img.threeD {
            height: 100%;
            width: auto;
            display: block;
        }
    </style>
    <link href="/css/listProduct.css" rel="stylesheet" type="text/css">

</head>

<body class="default-font">

<jsp:include page="/layout/toolbar.jsp"/>
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
<div class="container">

    <div class="page-header text-info">
        <h3>${menu=='manage' ? '상품관리' :'상품목록조회'}</h3>
    </div>

    <div class="row">
        <%--이 폼태그를 전달하는 건 1,2,3,4클릭이나 검색할때만임. --%>
        <div class="col-md-6 text-left">
            <p class="text-primary">
                전체 ${totalCount} 건수, 현재 ${requestScope.resultPage.currentPage} 페이지
            </p>
        </div>
        <form class="form-inline" name="detailForm">

            <div class="col-md-6 text-right">
                <div class="form-group">
                    <select name="searchCondition"
                            class="form-control">
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
                    </select>
                </div>
                <div class="form-group">
                    <label class="sr-only" for="searchKeyword">검색어</label>
                    <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"
                           placeholder="검색어"
                           value="${! empty search.searchKeyword ? search.searchKeyword : '' }">
                </div>

                <button type="button" class="btn btn-default">검색</button>
                <input type="hidden" id="currentPage" name="currentPage" value=""/>
            </div>
            <div class="col-md-12 text-right">
                <div class="form-group">

                    <input type="radio" id="searchType1" name="searchType" value="1"/>
                    <label for="searchType1" class="button">일반 검색</label>

                    <input type="radio" id="searchType2" name="searchType" value="2"/>
                    <label for="searchType2" class="button">가격 높은순 검색</label>

                    <input type="radio" id="searchType3" name="searchType" value="3"/>
                    <label for="searchType3" class="button">가격 낮은순 검색</label>

                    <input type="text" id="searchBoundFirst" name="searchBoundFirst"
                           value='${search.searchBoundFirst}'/>
                    부터
                    <input type="text" id="searchBoundEnd" name="searchBoundEnd" value='${search.searchBoundEnd}'/>
                    까지

                    <button type="button" data-searchBound>검색</button>

                </div>
            </div>
        </form>
    </div>

<%--    <div class="imgWrapper">--%>
<%--        <img class="threeD" src="https://search3.kakaocdn.net/argon/229x0_80_wr/EDQbl0Cz2S0"/>--%>
<%--    </div>--%>
    <div class="row">
        <c:set var="i" value="0"/>
        <c:forEach var="product" items="${list}">
            <c:set var="i" value="${i+1}"/>
            <div class="col-sm-6 col-md-4">
                <p>No : ${i}</p>
                <div class="thumbnail">
                    <c:forEach var="fileName" items="${fileNameList}">
                        <div class="col-xs-8 col-md-4 imgWrapper">
                            <img class="threeD" src="${pageContext.request.contextPath}/images/uploadFiles/${fileName}"/>
                        </div>
                    </c:forEach>
                    <div class="caption">
                        <h3>상품명 : ${product.prodName}</h3>
                        <p>상품설명 : </p>
                        <p>가격 : ${product.price}</p>
                        <p>현재상태 : </p>
                        <c:if test="${product.proTranCode!=null}">
                            <c:set var="resultA" value="${product.proTranCode.trim() == 'a' ? '판매중' : ''}"/>

                            <c:set var="resultB" value="${product.proTranCode.trim() == 'b' ? '판매완료' : ''}"/>
                            <c:set var="resultB2" value=""/>
                            <c:if test="${menu == 'manage' || menu == 'ok'}">
                                <c:set var="resultB2" value="${product.proTranCode.trim() == 'b' ? '배송하기' : ''}"/>
                            </c:if>
                            <c:set var="resultC" value="${product.proTranCode.trim() == 'c' ? '배송중' : ''}"/>
                            <c:set var="resultD" value="${product.proTranCode.trim() == 'd' ? '배송완료' : ''}"/>
                            ${resultA}${resultB}${(!empty resultB) ? '&nbsp;&nbsp;' : ''}
                            <span class="clickableSpan" data-update
                                  data-prodNo="${product.prodNo}">${resultB2}</span>${resultC}${resultD}
                        </c:if>
                        <c:if test="${product.proTranCode=='a'}">
                            <button type="button" class="btn btn-primary" data-getProduct
                                    data-prodNo="${product.prodNo}">상품보기
                            </button>
                        </c:if>
                        <c:if test="${!(product.proTranCode=='a')}">
                            <button type="button" class="disabled btn btn-primary" data-getProduct
                                    data-prodNo="${product.prodNo}">상품보기
                            </button>
                        </c:if>
                        <button type="button" data-setLike data-prodNo="${product.prodNo}">
                            찜하기
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="${pageContext.request.contextPath}/common/pageNavigator_new.jsp"/>

    <div class="col-md-12 text-right">
        <button type="button" class="btn btn-primary" data-toTable>테이블로 보기<span aria-hidden="true"> &nbsp&rarr;</span>
        </button>
    </div>


    <!--  페이지 Navigator 끝 -->


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
        $('button[data-toTable]').click(function () {
            window.location.href = "/product/listProduct?menu=${menu}";
        })

        $('input[name="searchKeyword"]').autocomplete({
            source: function (request, response) {

                function createLabelValueMapper(Prop) {
                    return function (object) {
                        return {
                            label: object[Prop],
                            value: object[Prop]
                        }
                    }
                };
                let searchCondition = $('select[name=searchCondition]').val()

                console.log("소스가 호출됩니다.");
                console.log("줄 데이터 : request.term : " + request.term);

                console.log("줄 데이터 : Condition : " + searchCondition);
                console.log(JSON.stringify({
                    searchKeyword: request.term,
                    searchCondition: searchCondition
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
                        searchKeyword: request.term,
                        searchCondition: searchCondition
                    }),
                }).done(function (rawData, textStatus, jqXHR) {
                    console.log('받은 데이터 : ' + rawData);
                    if (rawData.length !== 0) {
                        // 서버로부터 받은 데이터 (Product 객체의 리스트)를 처리
                        console.log('데이터가 존재 data : ' + rawData);
                        //번호, 명, 가격 $.map은 요소에 function 다 적용시키고 배열 반환
                        //그 function을 만드는 function을 만들면 더 편하겠다. 그게 mapper변수

                        switch (searchCondition) {
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
                        $.each(data, function (index, obj) {
                            console.log(obj);
                        })
                        console.log("data[0] : " + JSON.stringify(data[0]));
                        let matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(request.term), "i");
                        console.log($.grep(data, function (item) {
                            return matcher.test(item.label);
                        }));
                        response($.grep(data, function (item) {
                            return matcher.test(item.label);
                        }).slice(0, 5));
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
            select: function (event, ui) {
                // 옵션을 선택했을 때, 원하는 동작을 정의할 수 있음
                // 예를 들어, 선택된 Product의 id를 어딘가에 저장하거나 다른 작업을 수행
                console.log("Selected: " + ui.item.label + ", id: " + ui.item.value);
                let $element = $('input[name="searchKeyword"]');

                $element.addClass('flash-effect');
                $element.one('animationed', function () {
                    $element.removeClass('flash-effect');
                });
            },//end of select event
            classes: {
                "ui-autocomplete": "custom-ui-autocomplete",
            },

            autoFocus: true,//end of autoFocus

        });//end of autocomplete

        let imgWrapper = $("div.imgWrapper");
        let oneTime = true;

        imgWrapper.on('mouseIn', function (e) {
            console.log("얘는 들어올때 한번만 실행되어야 함.");
            //  imgWrapper.css('transition', 'transform 0.5s');
        });

        imgWrapper.on('mousemove', function (e) {
            let x = e.offsetX//0~240  0일때 rotateY(20deg) /240일때 rotateY(-20deg)
            //         20 = 0*a + b           / -20 = 240*a + b
            let y = e.offsetY//0~300 0일때 rotateX(-20deg) /300일때 rotateX(20deg)
            //      -20 = 0*a + b           20 = 300*a + b
            console.log(x, y);//

            let rotateY = -40 / 240 * x + 20
            let rotateX = 40 / 300 * y - 20
            console.log(rotateY, rotateX);

            $(this).css('transform', 'perspective(350px) rotateX(' + rotateX + 'deg) rotateY(' + rotateY + 'deg)');

            if (oneTime) {
                $(this).css('transition', 'transform 0.3s');
                oneTime = false;
            }
            console.log($(this)[0].style.transform);
        });

        imgWrapper.on('mouseout', function (e) {
            oneTime = true;
            $(this).css('transition', 'transform 0.5s');
            $(this).css('transform', 'perspective(350px) rotateX(' + 0 + 'deg) rotateY(' + 0 + 'deg)');
            console.log($(this)[0].style.transform);
        });
        imgWrapper.on('transitionend', function () {
            // transition 효과가 끝나면 transition 속성 제거
            $(this).css('transition', '');
        });

        // $.ui.autocomplete.prototype._close = function(event) {
        //     // 닫힘 동작을 무시하거나 조건에 따라 처리
        // };
    });//end of ready
</script>
</body>

</html>