package com.mitocode.ServicesImpl;

import com.mitocode.Models.Roles;
import com.mitocode.Models.Users;
import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Repository.RoleRepository;
import com.mitocode.Repository.UsersRepository;
import com.mitocode.Security.User;
import com.mitocode.Services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<Users, String> implements IUserService {

    private final UsersRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected IGenericRepo<Users, String> getRepo() {
        return userRepository;
    }


    @Override
    public Mono<Users> saveHash(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(role -> roleRepository.findById(role.getId()))
                        .map(Roles::getName)
                        .collectList()
                        .map(roles -> new User(user.getUsername(), user.getPassword(), user.getStatus(), roles))
                );
    }

}
