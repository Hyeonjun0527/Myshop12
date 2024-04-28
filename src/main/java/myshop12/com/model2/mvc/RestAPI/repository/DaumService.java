package myshop12.com.model2.mvc.RestAPI.repository;

import reactor.core.publisher.Mono;

public interface DaumService {
    public Mono<String> searchImage(String keyword) throws Exception;
}