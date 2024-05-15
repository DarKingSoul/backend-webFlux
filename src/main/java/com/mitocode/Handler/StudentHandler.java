package com.mitocode.Handler;

import com.mitocode.DTO.StudentsDto;
import com.mitocode.Models.Students;
import com.mitocode.ServicesImpl.StudentServiceImpl;
import com.mitocode.Validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class StudentHandler {

    private final StudentServiceImpl studentService;
    private final ModelMapper modelMapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findAll().map(this::convertToDto), StudentsDto.class);
    }

    public Mono<ServerResponse> findAllByAgeDesc(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findAllByAgeDesc().map(this::convertToDto), StudentsDto.class);
    }

    public Mono<ServerResponse> findAllByAgeAsc(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findAllByAgeAsc().map(this::convertToDto), StudentsDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return studentService.findById(request.pathVariable("id"))
                .flatMap(student -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(student)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(StudentsDto.class)
                .flatMap(validator::validate)
                .map(this::convertToEntity)
                .flatMap(studentService::save)
                .flatMap(student -> ServerResponse.created(URI.create("/v2/students/save/" + student.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(student)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(StudentsDto.class)
                .map(studentsDto -> {
                    studentsDto.setId(id);
                    return studentsDto;
                })
                .flatMap(validator::validate)
                .flatMap(student -> studentService.update(id, convertToEntity(student)))
                .flatMap(student -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToDto(student)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return studentService.delete(id)
                .flatMap(result -> result ? ServerResponse.noContent().build() : ServerResponse.notFound().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private StudentsDto convertToDto(Students student) {
        return modelMapper.map(student, StudentsDto.class);
    }

    private Students convertToEntity(StudentsDto studentDto) {
        return modelMapper.map(studentDto, Students.class);
    }

}
