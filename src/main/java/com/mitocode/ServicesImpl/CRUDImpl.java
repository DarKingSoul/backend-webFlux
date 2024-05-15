package com.mitocode.ServicesImpl;

import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Services.ICRUD;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public Mono<T> save(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Flux<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> update(ID id, T t) {
        return getRepo().findById(id)
                .flatMap(x -> getRepo().save(t));
    }

    @Override
    public Mono<Boolean> delete(ID id) {
        return getRepo().findById(id)
                .hasElement()
                .flatMap(result -> result
                        ? getRepo().deleteById(id).thenReturn(Boolean.TRUE)
                        : Mono.just(Boolean.FALSE)
                );
    }

}
