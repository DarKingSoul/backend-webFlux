package com.mitocode.Controller;

import com.mitocode.DTO.EnRollDto;
import com.mitocode.Models.EnRolls;
import com.mitocode.ServicesImpl.EnRollServiceImpl;
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
@RequestMapping("/enRoll")
@RequiredArgsConstructor
public class EnRollController {

    private final EnRollServiceImpl enRollService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/findAll")
    public Mono<ResponseEntity<Flux<EnRollDto>>> findAllEnRolls() {
        Flux<EnRollDto> enRollFlux = enRollService.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(enRollFlux))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findById/{id}")
    public Mono<ResponseEntity<EnRollDto>> findStudentById(@PathVariable("id") String id) {
        return enRollService.findById(id)
                .map(this::convertToDto)
                .map(course -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save")
    public Mono<ResponseEntity<EnRollDto>> saveStudent(@Valid @RequestBody EnRollDto course, final ServerHttpRequest request) {
        return enRollService.save(this.convertToEntity(course))
                .map(this::convertToDto)
                .map(course1 -> ResponseEntity
                        .created(URI.create(request.getURI().toString().concat("/").concat(course1.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{id}")
    public Mono<ResponseEntity<EnRollDto>> updateStudent(@Valid @PathVariable("id") String id, @RequestBody EnRollDto course) {
        return Mono.just(course)
                .map(course1 -> {
                    course1.setId(id);
                    return course1;
                })
                .flatMap(course1 -> enRollService.update(id, convertToEntity(course1))
                        .map(this::convertToDto)
                )
                .map(course1 -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course1)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteStudent(@PathVariable("id") String id) {
        return enRollService.delete(id)
                .flatMap(result -> result ? Mono.just(ResponseEntity.noContent().build())
                        : Mono.just(ResponseEntity.notFound().build())
                );
    }

    private EnRollDto convertToDto(EnRolls courses) {
        return modelMapper.map(courses, EnRollDto.class);
    }

    private EnRolls convertToEntity(EnRollDto enRollDto) {
        return modelMapper.map(enRollDto, EnRolls.class);
    }
    
}
