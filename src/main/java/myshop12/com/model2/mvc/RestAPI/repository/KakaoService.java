package myshop12.com.model2.mvc.RestAPI.repository;

import reactor.core.publisher.Mono;

public interface KakaoService {

    public Mono<String> getAccessToken(String code) throws Exception;
    public Mono<String> getUserInformation(String accessToken) throws Exception;
}
