package com.mitocode.ServicesImpl;

import com.mitocode.Models.Students;
import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Repository.StudentRepository;
import com.mitocode.Services.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Students, String> implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    protected IGenericRepo<Students, String> getRepo() {
        return studentRepository;
    }

    public Flux<Students> findAllByAgeDesc() {
        return studentRepository.findAll(Sort.by(Sort.Order.desc("age")));
    }

    public Flux<Students> findAllByAgeAsc() {
        return studentRepository.findAll(Sort.by(Sort.Order.asc("age")));
    }

}
