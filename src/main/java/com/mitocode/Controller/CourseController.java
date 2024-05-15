package com.mitocode.Controller;

import com.mitocode.DTO.CoursesDto;
import com.mitocode.Models.Courses;
import com.mitocode.ServicesImpl.CourseServiceImpl;
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
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseServiceImpl courseService;
    private final ModelMapper modelMapper;
    
    @GetMapping(value = "/findAll")
    public Mono<ResponseEntity<Flux<CoursesDto>>> findAllCourses() {
        Flux<CoursesDto> courseFlux = courseService.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(courseFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findById/{id}")
    public Mono<ResponseEntity<CoursesDto>> findStudentById(@PathVariable("id") String id) {
        return courseService.findById(id)
                .map(this::convertToDto)
                .map(course -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("@authorizeByRol.hasAccess()")
    @PostMapping(value = "/save")
    public Mono<ResponseEntity<CoursesDto>> saveStudent(@Valid @RequestBody CoursesDto course, final ServerHttpRequest request) {
        return courseService.save(this.convertToEntity(course))
                .map(this::convertToDto)
                .map(course1 -> ResponseEntity
                        .created(URI.create(request.getURI().toString().concat("/").concat(course1.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{id}")
    public Mono<ResponseEntity<CoursesDto>> updateStudent(@Valid @PathVariable("id") String id, @RequestBody CoursesDto course) {
        return Mono.just(course)
                .map(course1 -> {
                    course1.setId(id);
                    return course1;
                })
                .flatMap(course1 -> courseService.update(id, convertToEntity(course1))
                        .map(this::convertToDto)
                )
                .map(course1 -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("@authorizeByRol.hasAccess()")
    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteStudent(@PathVariable("id") String id) {
        return courseService.delete(id)
                .flatMap(result -> result ? Mono.just(ResponseEntity.noContent().build())
                        : Mono.just(ResponseEntity.notFound().build())
                );
    }
    
    private CoursesDto convertToDto(Courses courses) {
        return modelMapper.map(courses, CoursesDto.class);
    }

    private Courses convertToEntity(CoursesDto coursesDto) {
        return modelMapper.map(coursesDto, Courses.class);
    }

}
