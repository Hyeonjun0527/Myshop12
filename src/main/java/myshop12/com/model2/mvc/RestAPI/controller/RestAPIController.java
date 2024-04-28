package myshop12.com.model2.mvc.RestAPI.controller;

import myshop12.com.model2.mvc.RestAPI.repository.DaumService;
import myshop12.com.model2.mvc.RestAPI.repository.KakaoService;
import myshop12.com.model2.mvc.common.domain.DaumSearch;
import myshop12.com.model2.mvc.user.domain.User;
import myshop12.com.model2.mvc.user.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/rest/*")
public class RestAPIController {

    //Field
    private DaumService daumService;
    private KakaoService kakaoService;
    private UserService userService;

    @Autowired
    public void setDaumService(@Qualifier("daumServiceImpl") DaumService daumService) {
        this.daumService = daumService;
    }
    @Autowired
    public void setKakaoService(@Qualifier("kakaoServiceImpl") KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }
    @Autowired
    public void setUserService(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    //Constructor
    public RestAPIController() {
        System.out.println(this.getClass());
    }
    //Method
    @RequestMapping(value="json/searchImage",method = RequestMethod.POST)
    public Mono<ResponseEntity<DaumSearch>> searchImage(@RequestBody DaumSearch daumSearch,
                                                        Model model) throws Exception {
        System.out.println("/rest/json/searchImage : POST");

        Map<String,Object> map = new HashMap<String,Object>();
//        AtomicReference<String> resultValueStringRef = new AtomicReference<>();

//        String returnValue = String.valueOf(daumSearchService.searchImage("류현진"));
        //얘가 리턴하는게 Mono<String>이라서 String으로 변환해줘야함
        //subscribe의 역할은 Mono가 가지고 있는 값을 꺼내서 사용하는 것
        //Mono에는 map함수가 있음.
//        List<String> resultList  = daumService.searchImage(daumSearch.getName())
//                .map(
//                        result -> {
//                            System.out.println("result : " + result);
//                            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
//                            JSONArray documents = (JSONArray) jsonObject.get("documents");
//                            JSONObject document = (JSONObject) documents.get(0);
//                            String imageUrl = (String) document.get("image_url");
//                            String thumbnail_url = (String) document.get("thumbnail_url");
//                            System.out.println("imageUrl : " + imageUrl);
//                            System.out.println("thumbnail_url : " + thumbnail_url);
//                            return List.of(imageUrl,thumbnail_url);
//
//                        }
//                ).block();
//        System.out.println("resultValue :: "+resultList);
//        daumSearch.setImageUrl(resultList.get(0));
//        daumSearch.setThumbnailUrl(resultList.get(1));
//        return ResponseEntity.status(HttpStatus.OK).body(daumSearch);

        return daumService.searchImage(daumSearch.getName())
                .map(
                        result -> {
                            System.out.println("result : " + result);
                            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
                            JSONArray documents = (JSONArray) jsonObject.get("documents");
                            JSONObject document = (JSONObject) documents.get(0);
                            String imageUrl = (String) document.get("image_url");
                            String thumbnail_url = (String) document.get("thumbnail_url");
                            System.out.println("imageUrl : " + imageUrl);
                            System.out.println("thumbnail_url : " + thumbnail_url);
//                            return List.of(imageUrl,thumbnail_url);
                            daumSearch.setImageUrl(imageUrl);
                            daumSearch.setThumbnailUrl(thumbnail_url);
                            return ResponseEntity.status(HttpStatus.OK).body(daumSearch);
                        }
                );
    }

    @RequestMapping(value = "json/loginKakao", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> loginKakao(@RequestParam(value = "code") String authorize_code, HttpSession session) throws Exception {
        System.out.println("/rest/json/loginKakao : POST");
        System.out.println("code : " + authorize_code);

        Mono<ResponseEntity<?>> responseEntityMono = kakaoService.getAccessToken(authorize_code)
                .publishOn(Schedulers.boundedElastic())
                //여기서 받은건 body를  Mono<String>으로 변환한걸 받고 Mono<ResponseEntity<?>>로 변환
                .map(result -> {
                    System.out.println("result : " + result);
                    JSONObject ResultJsonObject = (JSONObject) JSONValue.parse(result);
                    AtomicReference<String> userId= new AtomicReference<>("");

                    try {
                        kakaoService.getUserInformation(ResultJsonObject.get("access_token")+"")
                                .doOnSubscribe(subscription -> System.out.println("Request subscribed"))
                                .doOnNext(response -> System.out.println("Received response: " + response))
                                .doOnError(error -> System.out.println("Error occurred: " + error.getMessage()))
                                .log()
                                .map(response->{
                                    System.out.println("response : " + response);
                                    JSONObject responseJsonObject = (JSONObject) JSONValue.parse(response);
                                    JSONObject profileJsonObject = (JSONObject) ( (JSONObject) (responseJsonObject.get("kakao_account")) ).get("profile");
                                    String nickname = (String) profileJsonObject.get("nickname");
                                    String id= responseJsonObject.get("id").toString();
                                    userId.set(id);
                                    User user = new User();
                                    user.setUserId(id);
                                    user.setUserName(nickname);
                                    user.setPassword("1111");
                                    user.setRole("user");
                                    try {
                                        System.out.println("userService :: " + userService.getUser(id));
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    try {
                                        //디비에 있으면 넘어가고 없으면 넣는다.
                                        Optional.ofNullable(userService.getUser(id)).ifPresentOrElse((user1)->{},()-> {
                                            try {
                                                System.out.println("디비에 넣겠다.");
                                                userService.addUser(user);
                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }
                                        });

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                    session.setAttribute("user", user);

                                    return response;
                                }).block();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

//                    return ResponseEntity.status(HttpStatus.OK).body(result);
                    return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                            .header(HttpHeaders.LOCATION, "http://127.0.0.1:3000/?access_token="+ ResultJsonObject.get("access_token")+"&userId="+ userId)
                            .build();
                });

        /*
        * HTTP/1.1 200 OK
            Content-Type: application/json;charset=UTF-8
            {
                "token_type":"bearer",
                "access_token":"${ACCESS_TOKEN}",
                "expires_in":43199,
                "refresh_token":"${REFRESH_TOKEN}",
                "refresh_token_expires_in":5184000,
                "scope":"account_email profile"
            }
        * */
        return responseEntityMono;
    }

}
