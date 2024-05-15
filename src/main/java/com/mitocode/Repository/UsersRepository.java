package com.mitocode.Repository;

import com.mitocode.Models.Users;
import reactor.core.publisher.Mono;

public interface UsersRepository extends IGenericRepo<Users, String> {

    Mono<Users> findOneByUsername(String username);

}
