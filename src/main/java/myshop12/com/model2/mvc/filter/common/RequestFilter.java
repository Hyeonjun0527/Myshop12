package myshop12.com.model2.mvc.filter.common;

import javax.servlet.*;


import java.io.IOException;


/*
 * javax.servlet.Fiter  를 구현한  Filter
 * 
 * Servlet Meta-data 인 web.xml 에 아래와 같이 등록
    <filter>
		<filter-name>requestFilter</filter-name>
		<filter-class>com.model2.mvc.common.filter.RequestFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>requestFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
*   
*   모든 URI 즉 모든 request 는 필터를 통과하게하여 한글처리 
 */
public class RequestFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}
	
	public void doFilter(	ServletRequest request ,
											ServletResponse response ,
											FilterChain filterChain) 
										throws IOException, ServletException {

//		User u = new User();
//		////////////////////////////////////////////////////디버깅////////////////////////////////////////
//		u.setAddr("서울시 강남구 역삼동");
//		u.setUserId("user12");//user12,admin
//		u.setUserName("scott");//scott, admin
//		u.setRole("user");//user, admin
//		u.setEmail("immio0@naver.com");
//		u.setPassword("1234");
//		u.setPhone("010-1234-5678");
//		u.setSsn("900101");
//		((HttpServletRequest) request).getSession().setAttribute("user",u);

		filterChain.doFilter(request, response);
	}

	public void destroy() {
	}

}