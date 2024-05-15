package com.mitocode.Controller;

import com.mitocode.DTO.StudentsDto;
import com.mitocode.Enums.Variables;
import com.mitocode.Models.Students;
import com.mitocode.ServicesImpl.StudentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;
    private final ModelMapper modelMapper;

    @PreAuthorize("@authorizeByRol.hasAccess()")
    @GetMapping(value = "/findAll")
    public Mono<ResponseEntity<Flux<StudentsDto>>> findAllStudents() {
        Flux<StudentsDto> studentFlux = studentService.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findAllByAgeDesc")
    public Mono<ResponseEntity<Flux<StudentsDto>>> findAllStudentsByAgeDesc() {
        Flux<StudentsDto> studentFlux = studentService.findAllByAgeDesc()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findAllByAgeAsc")
    public Mono<ResponseEntity<Flux<StudentsDto>>> findAllStudentsByAgeAsc() {
        Flux<StudentsDto> studentFlux = studentService.findAllByAgeAsc()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findById/{id}")
    public Mono<ResponseEntity<StudentsDto>> findStudentById(@PathVariable("id") String id) {
        return studentService.findById(id)
                .map(this::convertToDto)
                .map(student -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save")
    public Mono<ResponseEntity<StudentsDto>> saveStudent(@Valid @RequestBody StudentsDto student, final ServerHttpRequest request) {
        return studentService.save(this.convertToEntity(student))
                .map(this::convertToDto)
                .map(student1 -> ResponseEntity
                        .created(URI.create(request.getURI().toString().concat("/").concat(student1.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{id}")
    public Mono<ResponseEntity<StudentsDto>> updateStudent(@Valid @PathVariable("id") String id, @RequestBody StudentsDto student) {
        return Mono.just(student)
                .map(student1 -> {
                    student1.setId(id);
                    return student1;
                })
                .flatMap(student1 -> studentService.update(id, convertToEntity(student1))
                        .map(this::convertToDto)
                )
                .map(student1 -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteStudent(@PathVariable("id") String id) {
        return studentService.delete(id)
                .flatMap(result -> result ? Mono.just(ResponseEntity.noContent().build())
                        : Mono.just(ResponseEntity.notFound().build())
                );
    }

    private StudentsDto convertToDto(Students student) {
        return modelMapper.map(student, StudentsDto.class);
    }

    private Students convertToEntity(StudentsDto studentDto) {
        return modelMapper.map(studentDto, Students.class);
    }

}
