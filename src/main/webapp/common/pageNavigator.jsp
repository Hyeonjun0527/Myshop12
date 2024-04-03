<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- 요청페이지 <= 페이지번들당 페이지 수. 이전  3  <= 5 이면 더이상 이전 못누르게 함!-->
	<c:if test="${resultPage.currentPage <= resultPage.pageUnit	}">
			◀ 이전
	</c:if>
	<!-- 요청페이지 <= 페이지번들당 페이지 수. 이전  6 > 5 이면 누를 수 있게 함!-->
<%--6---%>
	<c:if test="${resultPage.currentPage > resultPage.pageUnit }">
	<!--   [[[6-5 = 1]]]    [[[11-5 = 6]]]  !-->
			<a href="javascript:fncGetList('${resultPage.beginUnitPage-resultPage.pageUnit}')">◀ 이전</a>
	</c:if>
	<!--   6부터 11    !-->
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${ i }')">${ i }</a>
	</c:forEach>
	
	<!--  11 >= 8 페이지가 11까지 나왔는데 8이 최대페이지수라면 안나와야해 -->
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	<!-- 위의 반대라면 나오게 해야함 -->
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if>