$(function() {

    //==> 개인정보조회 Event 연결처리부분
    //==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
    $(".item:contains('login')").on("click", function () {
        //Debug..
        //alert(  $( ".Depth03:contains('개인정보조회')" ).html() );
        $(window.parent.frames["rightFrame"].document.location).attr("href", "/user/loginView.jsp");
    });

    $(".item:contains('logout')").on("click", function () {
        //Debug..
        //alert(  $( ".Depth03:contains('개인정보조회')" ).html() );
        $(window.parent.document.location).attr("href", "/user/logout");
    });
});