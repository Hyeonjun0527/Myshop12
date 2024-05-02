package myshop12.com.model2.mvc.user.controller;

import javax.servlet.http.HttpSession;
import myshop12.com.model2.mvc.common.domain.Page;
import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.user.domain.User;
import myshop12.com.model2.mvc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;


//==> 회원관리 Controller
@Controller
@RequestMapping("user/*")
public class UserController {

	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음

	public UserController(){
		System.out.println(this.getClass());
	}

	@Value("${pageUnit}")
	int pageUnit;

	@Value("${pageSize}")
	int pageSize;


	//@RequestMapping("/addUserView.do")
	//public String addUserView() throws Exception {
	@RequestMapping( value="addUser", method=RequestMethod.GET )
	public String addUser() throws Exception{

		System.out.println("/user/addUser : GET");

		return "redirect:/user/addUserView.jsp";
	}

	//@RequestMapping("/addUser.do")
	@RequestMapping( value="addUser", method=RequestMethod.POST )
	public String addUser( @ModelAttribute("user") User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		userService.addUser(user);

		return "redirect:/user/loginView.jsp";
	}

	//@RequestMapping("/getUser.do")
	@RequestMapping( value="getUser", method=RequestMethod.GET )
	public String getUser( @RequestParam("userId") String userId , Model model ) throws Exception {

		System.out.println("/user/getUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);

		return "forward:/user/getUser.jsp";
	}

	//@RequestMapping("/updateUserView.do")
	//public String updateUserView( @RequestParam("userId") String userId , Model model ) throws Exception{
	@RequestMapping( value="updateUser", method=RequestMethod.GET )
	public String updateUser( @RequestParam("userId") String userId , Model model ) throws Exception{

		System.out.println("/user/updateUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);

		return "forward:/user/updateUser.jsp";
	}

	//@RequestMapping("/updateUser.do")
	@RequestMapping( value="updateUser", method=RequestMethod.POST )
	public String updateUser( @ModelAttribute("user") User user , Model model , HttpSession session) throws Exception{

		System.out.println("/user/updateUser : POST");
		//Business Logic
		userService.updateUser(user);//이 메서드는 user의 내부 속성까지 바꾼다. 그게 마이배티스의 힘이다.

		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}

		//return "redirect:/getUser.do?userId="+user.getUserId();
		return "redirect:/user/getUser?userId="+user.getUserId();
	}

	//@RequestMapping("/loginView.do")
	//public String loginView() throws Exception{
	@RequestMapping( value="login", method=RequestMethod.GET )
	public String login() throws Exception{

		System.out.println("/user/logon : GET");

		return "redirect:/user/loginView.jsp";
	}

	//@RequestMapping("/login.do")
	@RequestMapping( value="login", method=RequestMethod.POST )
	public String login(@ModelAttribute("user") User user ,
						HttpSession session ) throws Exception{

		System.out.println("/user/login : POST");
		System.out.println("user :: " + user);
		//Business Logic
		Optional<User> OptionalDBUser=Optional.ofNullable(userService.getUser(user.getUserId()));

		//dbUser가 있으면 비번검증후 세션셋하고 없으면 아무것도 안함
		//패스워드가 user객체의 패스워드와 같으면 세션셋.

		//map filter 값이 있을때만 수행 없으면 빈 Optional 리턴. 리턴타입 Optional<T>
		//ifPresent 값이 있을때만 수행 없으면 아무것도 수행하지 않음. 리턴타입 Void
		//orElse은 Optional벗기고, 값이 있으면 리턴 없으면 설정한 값 리턴타입 T

		//값변형하고 싶어. map
		//걸러내고싶어.(조건 만족하면 값변형X리턴하고 싶어) filter
		//Optional 벗기고싶어. ofElse
		//함수실행하고 싶어. ifPresent

		//나는 지금
		//user로부터 패스워드 얻고 싶어. map
		//Optional 벗기고 싶어 . ofElse
		OptionalDBUser.filter(dbUser -> dbUser.getPassword().equals(
						Optional.ofNullable(user)
								.map(User::getPassword)
								.orElse("")))
				.ifPresent(dbUser -> session.setAttribute("user", dbUser));

//		이것도 같은코드임.
//		OptionalDBUser.ifPresentOrElse((dbUser)->{
//				if(dbUser.getPassword().equals(
//						Optional.ofNullable(user)
//								.map(User::getPassword)
//								.orElse(""))) session.setAttribute("user",dbUser)},
//						()->{}
//		);

		return "redirect:/index.jsp";
	}

	//@RequestMapping("/logout.do")
	@RequestMapping( value="logout", method=RequestMethod.GET )
	public String logout(HttpSession session ) throws Exception{

		System.out.println("/user/logout : POST");

		session.invalidate();

		return "redirect:/index.jsp";
	}//end of logout


	//@RequestMapping("/checkDuplication.do")
	@RequestMapping( value="checkDuplication", method=RequestMethod.POST )
	public String checkDuplication( @RequestParam("userId") String userId , Model model ) throws Exception{

		System.out.println("/user/checkDuplication : POST");
		//Business Logic
		boolean result=userService.checkDuplication(userId);
		// Model 과 View 연결
		model.addAttribute("result", new Boolean(result));
		model.addAttribute("userId", userId);

		return "forward:/user/checkDuplication.jsp";
	}//end of checkDuplication

	//@RequestMapping("/listUser.do")
	@RequestMapping( value="listUser" )
	public String listUser(@ModelAttribute("search") Search search , Model model) throws Exception{

		System.out.println("/user/listUser : GET / POST");

		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		// Business logic 수행
		Map<String , Object> map=userService.getUserList(search);

		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);

		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/user/listUser.jsp";
	}//end of listUser

}