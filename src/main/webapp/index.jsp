<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>


<!-- ///////////////////////// JSTL ////////////////////////// -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    try {
        ResourceBundle apiKeyBundle = ResourceBundle.getBundle("config.apiKey", new Locale("ko", "KR"));
        ResourceBundle myIpBundle = ResourceBundle.getBundle("config.myIp", new Locale("ko", "KR"));
        String apiKey = apiKeyBundle.getString("kakao.api.key");
        String apiUrl = myIpBundle.getString("my.jsp.ip");
        System.out.println("apiKey : " + apiKey);
        System.out.println("apiUrl : " + apiUrl);
    }catch(Exception e){
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
%>
<!-- ///////////////////////////// 로그인시 Forward /////////////////////////////////////// -->
<c:if test="${ ! empty user }">
    <jsp:forward page="main.jsp"/>
</c:if>
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////// -->


<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">

    <!-- 참조 : http://getbootstrap.com/css/   -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!--  ///////////////////////// CSS ////////////////////////// -->
    <!--  ///////////////////////// JavaScript ////////////////////////// -->


</head>

<body class="default-font">

<!-- ToolBar Start /////////////////////////////////////-->
<div class="navbar  navbar-default">

    <div class="container">

        <a class="navbar-brand" href="#">Model2 MVC Shop</a>

        <!-- toolBar Button Start //////////////////////// -->
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#target">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- toolBar Button End //////////////////////// -->

        <div class="collapse navbar-collapse" id="target">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">회원가입</a></li>
                <li><a href="#">로 그 인</a></li>
            </ul>
        </div>

    </div>
</div>
<!-- ToolBar End /////////////////////////////////////-->

<!--  화면구성 div Start /////////////////////////////////////-->
<div class="container">

    <!-- 다단레이아웃  Start /////////////////////////////////////-->
    <div class="row">

        <!--  Menu 구성 Start /////////////////////////////////////-->
        <div class="col-md-3">

            <!--  회원관리 목록에 제목 -->
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-heart"></i> 회원관리
                </div>
                <!--  회원관리 아이템 -->
                <ul class="list-group">
                    <li class="list-group-item">
                        <a href="#">개인정보조회</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                    <li class="list-group-item">
                        <a href="#">회원정보조회</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                </ul>
            </div>


            <div class="panel panel-primary">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-briefcase"></i> 판매상품관리
                </div>
                <ul class="list-group">
                    <li class="list-group-item">
                        <a href="#">판매상품등록</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                    <li class="list-group-item">
                        <a href="#">판매상품관리</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                </ul>
            </div>


            <div class="panel panel-primary">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-shopping-cart"></i> 상품구매
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><a href="#">상품검색</a></li>
                    <li class="list-group-item">
                        <a href="#">구매이력조회</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                    <li class="list-group-item">
                        <a href="#">최근본상품</a> <i class="glyphicon glyphicon-ban-circle"></i>
                    </li>
                </ul>
            </div>

        </div>
        <!--  Menu 구성 end /////////////////////////////////////-->

        <!--  Main start /////////////////////////////////////-->
        <div class="col-md-9">
            <div class="jumbotron">
                <h1>Model2 MVC Shop</h1>
                <p>로그인 후 사용가능...</p>
                <p>로그인 전 검색만 가능합니다.</p>
                <p>회원가입 하세요.</p>

                <div class="text-center">
                    <a class="btn btn-info btn-lg" href="#" role="button">회원가입</a>
                    <a class="btn btn-info btn-lg" href="#" role="button">로 그 인</a>
                    <button
                            onclick="handleKakaoLogin()"
                            style="border: 0; padding: 0;"
                    >
                        <img
                                src="${pageContext.request.contextPath}/images/kakao_login_medium_narrow.png"
                                alt="카카오 로그인"
                                class="img-rounded"
                        />
                    </button>
                </div>
            </div>
            <!--  Main end /////////////////////////////////////-->

            <div class="col-md-9">
                <div class="jumbotron">

                    <div class="text-center">
                        <input type="text" id="daumInput" placeholder="랜덤으로 이미지가 나와요 ">
                        <button class="grow_skew_backward btn-lg" id="submitButton">제출</button>
                        <img id="daumImage" src="" alt="이미지가 없습니다." style="width:400px; height:auto;"/>
                    </div>


                </div>
            </div>
        </div>
        <!-- 다단레이아웃  end /////////////////////////////////////-->

    </div>
</div>
    <!--  화면구성 div end /////////////////////////////////////-->
</body>
<script type="text/javascript">

    const handleKakaoLogin = (event) => {
        if (event) {
            event.preventDefault(); // 기본 동작 막기
        }
        console.log("카카오 로그인 버튼 클릭");
        //apiKey,ip el/jstl 사용

        const redirectUri = "http://192.168.0.65:8080"+"/rest/json/loginKakao";
        const encodedUri = encodeURIComponent(redirectUri);
        console.log(encodedUri);
        const clientId = "ec7e721192a2cfab01f1ebbf33379077";
        const redirectUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + encodedUri;

        window.location.href =
            redirectUrl;
    };


    //============= 회원가입 화면이동 =============
    $(function () {



        //==> 추가된부분 : "addUser"  Event 연결
        $("a[href='#' ]:contains('회원가입')").on("click", function () {
            self.location = "user/addUser"
        });
        //============= 로그인 화면이동 =============

        //==> 추가된부분 : "addUser"  Event 연결
        $("a[href='#' ]:contains('로 그 인')").on("click", function () {
            self.location = "user/login"
        });

        $('#submitButton').click(function () {
            let daumInput = $('#daumInput').val();

            console.log(daumInput);

            $.ajax(
                {
                    url: "/rest/json/searchImage",
                    method: "POST",
                    dataType: "json",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify({
                        "name": daumInput
                    }),
                }
            ).done(function (data, status, xhr) {
                //받은 데이터는 제이슨일거야.
                console.log(data);
                console.log("받은 데이터 : " + data.imageUrl);
                // $("#daumImage").prop("src", data.imageUrl);
                console.log("썸네일 데이터 : " + data.thumbnailUrl);
                $("#daumImage").prop("src", data.imageUrl);
            }).fail(function (xhr, status, error) {
                console.log("요청 실패: " + status + ", " + error);
            });
        });
    });


</script>

</html>