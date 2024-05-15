package com.mitocode.Handler;

import com.mitocode.DTO.CoursesDto;
import com.mitocode.Models.Courses;
import com.mitocode.ServicesImpl.CourseServiceImpl;
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
public class CourseHandler {

    private final CourseServiceImpl courseService;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(courseService.findAll().map(this::convertToDto), CoursesDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return courseService.findById(request.pathVariable("id"))
                .flatMap(course -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(course)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(CoursesDto.class)
                .flatMap(validator::validate)
                .map(this::convertToEntity)
                .flatMap(courseService::save)
                .flatMap(course -> ServerResponse.created(URI.create("/v2/courses/save/" + course.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(course)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(CoursesDto.class)
                .map(coursesDto -> {
                    coursesDto.setId(id);
                    return coursesDto;
                })
                .flatMap(validator::validate)
                .flatMap(course -> courseService.update(id, convertToEntity(course)))
                .flatMap(course -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(course)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return courseService.delete(id)
                .flatMap(result -> result ? ServerResponse.noContent().build() : ServerResponse.notFound().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private CoursesDto convertToDto(Courses course) {
        return modelMapper.map(course, CoursesDto.class);
    }

    private Courses convertToEntity(CoursesDto courseDto) {
        return modelMapper.map(courseDto, Courses.class);
    }
    
}
