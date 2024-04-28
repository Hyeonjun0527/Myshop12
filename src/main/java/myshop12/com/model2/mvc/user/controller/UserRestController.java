package myshop12.com.model2.mvc.user.controller;


import javax.servlet.http.HttpSession;

import myshop12.com.model2.mvc.common.domain.Page;
import myshop12.com.model2.mvc.common.domain.Search;
import myshop12.com.model2.mvc.user.domain.User;
import myshop12.com.model2.mvc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {

    ///Field
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    //setter Method 구현 않음

    public UserRestController() {
        System.out.println(this.getClass());
    }

    @Value("${pageUnit}")
    int pageUnit;

    @Value("${pageSize}")
    int pageSize;

    //	@RequestMapping(value="json/addUser/{userId}",method=RequestMethod.POST)
//	public Map addUser() throws Exception{
//
//		System.out.println("/user/json/addUser : GET");
//
//		return null;
//	}
    @RequestMapping(value = "json/checkSession", method = RequestMethod.GET)
    public ResponseEntity<?> checkSession(HttpSession session) throws Exception {
        System.out.println("/user/json/checkSession : GET");
        return Optional.ofNullable(session.getAttribute("user"))
                .map((user) -> {
                    System.out.println("세션에 담긴 유저정보 user :: " + user);
                    return ResponseEntity.ok().body(Map.of("user",user));
                })
                .orElseGet(() -> {
                    System.out.println("세션에 담긴 유저정보 없음");
                    return ResponseEntity.noContent().build();
                });
    }

    @RequestMapping(value = "json/addUser", method = RequestMethod.POST)
    public Map<String, Object> addUser(@RequestBody User user) throws Exception {

        System.out.println("/user/json/addUser : POST");

        userService.addUser(user);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "ok");
        map.put("user", user);
        return map;
    }


    @RequestMapping(value = "json/getUser/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) throws Exception {
        System.out.println("/user/json/getUser : GET");
        //Business Logic
        return userService.getUser(userId);
    }

    @RequestMapping(value = "json/updateUser", method = RequestMethod.GET)
    public Map<String, Object> updateUser(@RequestParam String userId) throws Exception {

        System.out.println("/user/json/updateUser : GET");

        User userToSend = userService.getUser(userId);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("user", userToSend);
        map.put("message", "ok");
        return map;
    }

    @RequestMapping(value = "json/updateUser", method = RequestMethod.POST)
    public Map<String, Object> updateUser(@RequestBody User user,
                                          HttpSession session) throws Exception {

        System.out.println("/user/json/updateUser : POST");

        System.out.println("BL전 user :: " + user);
        userService.updateUser(user);
        System.out.println("BL후 user :: " + user);

        String sessionId = ((User) session.getAttribute("user")).getUserId();
        if (sessionId.equals(user.getUserId())) {
            session.setAttribute("user", user);
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("user", user);
        map.put("message", "ok");
        return map;
    }


    //클라가 필요한 데이터가 전혀 없다. 서버에 요청을 보낼 필요가 없다.
//	@RequestMapping(value = "json/login", method = RequestMethod.GET)
//	public Map<String,Object> login() throws Exception{
//
//		System.out.println("/user/json/login : GET");
//
//		return null;
//	}

    //클라가 데이터를 보내
    //컨트롤러가 데이터를 바인딩하고, 세션을 세팅해주고, 뷰는 건내주지 않아.
    //클라는 데이터만 받아. 그 데이터를 가지고, 자기의 UI에 네비게이션을 잘 하겠지.
    @RequestMapping(value = "json/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user,
                                      HttpSession session) throws Exception {
        System.out.println("/user/json/login : POST");
        //Business Logic
        System.out.println("리액트가 준 user::" + user);

        if (user == null || user.getPassword() == null) {
            // 유저 정보가 없거나 패스워드가 누락되었을 때 400 Bad Request 반환
            return ResponseEntity.badRequest().build();
        }

        Optional<User> dbUser = Optional.ofNullable(userService.getUser(user.getUserId()));

        return dbUser.filter(u -> u.getPassword().equals(user.getPassword()))
                .map(u -> {
                    System.out.println("json/login :: 세션에 담을 유저정보 user :: " + u);
                    session.setAttribute("user", u);
                    return ResponseEntity.ok().body(u);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 사용자를 찾을 수 없을 때 404 Not Found 반환
    }//모델어트리뷰트는 바인딩해주고, 데이터를 스콥에 담아줘. 그런데 클라는 스콥이란 개념이 없어서
    //json으로 데이터를 전달해줘야해.

    @RequestMapping(value = "json/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(HttpSession session) throws Exception {

        System.out.println("/user/json/logout : GET");

        session.invalidate();

        Map<String, Object> map = new HashMap<>();
        map.put("message", "ok");

        return map;
    }

    @RequestMapping(value = "json/listUser")
    public Map<String, Object> listUser(@RequestBody Search search) throws Exception {

        System.out.println("/user/json/listUser : GET / POST");

        if (search.getCurrentPage() == 0) {
            search.setCurrentPage(1);
        }
        search.setPageSize(pageSize);

        Map<String, Object> userMap = userService.getUserList(search);

        Page resultPage = new Page(search.getCurrentPage(), ((Integer) userMap.get("totalCount")).intValue(), pageUnit, pageSize);
        System.out.println(resultPage);

        Map<String, Object> map = new HashMap<>();

        map.put("list", userMap.get("list"));//list안에 user정보, search , resultPage 다 있음.
        map.put("resultPage", resultPage);
        map.put("search", search);
        map.put("message", "ok");

        return map;

    }

}