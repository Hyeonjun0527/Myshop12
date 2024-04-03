package myshop12.com.model2.mvc.RestAPI.controller;

import myshop12.com.model2.mvc.RestAPI.repository.DaumSearchService;
import myshop12.com.model2.mvc.common.domain.DaumSearch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest/*")
public class RestAPIController {

    //Field
    private DaumSearchService daumSearchService;

    @Autowired
    public void setDaumSearchService(@Qualifier("daumSearchServiceImpl") DaumSearchService daumSearchService) {
        this.daumSearchService = daumSearchService;
    }
    //Constructor
    public RestAPIController() {
        System.out.println(this.getClass());
    }
    //Method
    @RequestMapping(value="json/searchImage",method = RequestMethod.POST)
    public ResponseEntity<?> searchImage(@RequestBody DaumSearch daumSearch,
                              Model model) throws Exception {
        System.out.println("/rest/json/searchImage : POST");

        Map<String,Object> map = new HashMap<String,Object>();
//        AtomicReference<String> resultValueStringRef = new AtomicReference<>();

//        String returnValue = String.valueOf(daumSearchService.searchImage("류현진"));
        //얘가 리턴하는게 Mono<String>이라서 String으로 변환해줘야함
        //subscribe의 역할은 Mono가 가지고 있는 값을 꺼내서 사용하는 것
        String resultValue  = daumSearchService.searchImage(daumSearch.getName())
                .map(
                        result -> {
                            System.out.println("result : " + result);
                            JSONObject jsonObject = (JSONObject) JSONValue.parse(result);
                            JSONArray documents = (JSONArray) jsonObject.get("documents");
                            JSONObject document = (JSONObject) documents.get(0);
                            String imageUrl = (String) document.get("image_url");
                            System.out.println("imageUrl : " + imageUrl);
                            return imageUrl;
                        }
                ).block();


        System.out.println("resultValue :: "+resultValue);
        daumSearch.setImageUrl(resultValue);
        //엔트리포인트에서 그 복잡한 json중에 속성값 하나 꺼내서..

        //resultValue
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.readValue(resultValue, String.class);

        //여기서
        return ResponseEntity.status(HttpStatus.OK).body(daumSearch);
        //Mono<ResponseEntity<String>>는 최대 한 개의 ResponseEntity<String> 인스턴스를
        //비동기적으로 포함할 수 있는 리액티브 타입을 리턴하는 것을 의미
//        return daumSearchService.searchImage("류현진")
//                .map(result -> {
//                    System.out.println("returnValue : " + result);
//                    return ResponseEntity.ok(result);
//                });
    }

}
