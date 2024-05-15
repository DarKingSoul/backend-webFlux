package com.mitocode.Handler;

import com.mitocode.DTO.EnRollDto;
import com.mitocode.Models.EnRolls;
import com.mitocode.ServicesImpl.EnRollServiceImpl;
import com.mitocode.Validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class EnRollHandler {

    private final EnRollServiceImpl enRollService;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(enRollService.findAll().map(this::convertToDto), EnRollDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return enRollService.findById(request.pathVariable("id"))
                .flatMap(enRoll -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(enRoll)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(EnRollDto.class)
                .flatMap(validator::validate)
                .map(this::convertToEntity)
                .flatMap(enRollService::save)
                .flatMap(enRoll -> ServerResponse.created(URI.create("/v2/courses/save/" + enRoll.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(enRoll)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(EnRollDto.class)
                .map(enRollDto -> {
                    enRollDto.setId(id);
                    return enRollDto;
                })
                .flatMap(validator::validate)
                .flatMap(enRoll -> enRollService.update(id, convertToEntity(enRoll)))
                .flatMap(enRoll -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(enRoll)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return enRollService.delete(id)
                .flatMap(result -> result ? ServerResponse.noContent().build() : ServerResponse.notFound().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private EnRollDto convertToDto(EnRolls enRoll) {
        return modelMapper.map(enRoll, EnRollDto.class);
    }

    private EnRolls convertToEntity(EnRollDto enRollDto) {
        return modelMapper.map(enRollDto, EnRolls.class);
    }
    
}
