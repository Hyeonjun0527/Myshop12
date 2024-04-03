<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<body>
	
		<h3> NumberFormatException page</h3>
		<%	Exception exception = (Exception)request.getAttribute("exception");	%>
		<%="Java Code을 이용한 예외 Message 보기 ::" +  exception.getMessage() %><br/>
		EL을 이용한 예외 Message 보기 :: ${ exception.message }<br/> 
		
		<hr/>
		<br/>
		<%=  request.getRequestURI() %>
		<br/>
		<%=  request.getRequestURL() %>
	
	</body>
</html>