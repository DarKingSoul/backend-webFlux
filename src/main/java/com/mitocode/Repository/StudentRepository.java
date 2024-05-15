package com.mitocode.Repository;

import com.mitocode.Models.Students;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface StudentRepository extends IGenericRepo<Students, String> {

    @Query("{ 'age' : -1 }")
    Flux<Students> findAllByAgeDesc();

    @Query("{ 'age' : 1 }")
    Flux<Students> findAllByAgeAsc();

}
