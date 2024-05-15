package com.mitocode.Services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, ID> {

    Mono<T> save (T t);
    Mono<T> findById (ID id);
    Flux<T> findAll ();
    Mono<T> update (ID id, T t);
    Mono<Boolean> delete (ID id);
    
}
