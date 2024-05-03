package myshop12.com.model2.mvc.RestAPI.repository.impl;

import myshop12.com.model2.mvc.RestAPI.repository.KakaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


@Service("kakaoServiceImpl")
public class KakaoServiceImpl implements KakaoService {

    private final WebClient webClientAuth;
    private final WebClient webClientApi;
    @Value("${kakao.api.key}")
    private String apiKey;
    @Value("${my.jsp.ip}")
    private String myIp;

    public KakaoServiceImpl(){
        HttpClient httpClient = HttpClient.create();
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        this.webClientAuth = WebClient.builder().clientConnector(connector).baseUrl("https://kauth.kakao.com").build();
        this.webClientApi = WebClient.builder().clientConnector(connector).baseUrl("https://kapi.kakao.com").build();
    }


    public Mono<String> getAccessToken(String authorize_code) throws Exception {
        System.out.println("getAccessToken() 호출 :: 카카오서비스");
        String path = "/oauth/token";
        System.out.println(path);

        /*
        -H는 헤더 -d는 바디
        * curl -v -X POST "https://kauth.kakao.com/oauth/token" \
                 -H "Content-Type: application/x-www-form-urlencoded" \
                 -d "grant_type=authorization_code" \
                 -d "client_id=${REST_API_KEY}" \
                 --data-urlencode "redirect_uri=${REDIRECT_URI}" \
                 -d "code=${AUTHORIZE_CODE}"
        *라고 webClient가 보내야 해.
        * */
        return webClientAuth.post()
                .uri(path)
                .header(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData("grant_type","authorization_code")
                        .with("client_id",""+apiKey)
                        .with("redirect_uri",myIp+"/rest/json/loginKakao")
                        .with("code",""+authorize_code))
                .retrieve()//서버로 부터 응답 받기
                .bodyToMono(String.class);//응답 받은 본문을 Mono<String>으로 변환

    }

    public Mono<String> getUserInformation(String access_token) throws Exception {
        System.out.println("getUserInformation() 호출 :: 카카오서비스");
        String path = "/v2/user/me";
        System.out.println(path);

        return webClientApi.get()
                .uri(path)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+access_token)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class);
    }

}
