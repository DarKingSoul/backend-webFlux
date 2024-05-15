package com.mitocode.Services;

import com.mitocode.Models.Users;
import com.mitocode.Security.User;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<Users, String> {

    Mono<Users> saveHash(Users user);
    Mono<User> findByUsername(String username);

}
