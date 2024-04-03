package myshop12.com.model2.mvc.RestAPI.repository;

import reactor.core.publisher.Mono;

public interface DaumSearchService {
    public Mono<String> searchImage(String keyword) throws Exception;
}