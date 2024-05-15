package com.mitocode.Controller;

import com.mitocode.DTO.StudentsDto;
import com.mitocode.DTO.UserDto;
import com.mitocode.Models.Users;
import com.mitocode.ServicesImpl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/findAll")
    public Mono<ResponseEntity<Flux<UserDto>>> findAllUsers() {
        Flux<UserDto> usersFlux = userService.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(usersFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save")
    public Mono<ResponseEntity<UserDto>> saveUser(@Valid @RequestBody UserDto userDto, final ServerHttpRequest request) {
        return userService.saveHash(this.convertToEntity(userDto))
                .map(this::convertToDto)
                .map(user1 -> ResponseEntity
                        .created(URI.create(request.getURI().toString().concat("/").concat(user1.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(user1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private UserDto convertToDto(Users users) {
        return modelMapper.map(users, UserDto.class);
    }

    private Users convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, Users.class);
    }

}
