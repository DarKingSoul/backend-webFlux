package com.mitocode.ServicesImpl;

import com.mitocode.Models.Courses;
import com.mitocode.Repository.CourseRepository;
import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Services.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Courses, String> implements ICourseService {

    private final CourseRepository courseRepository;

    @Override
    protected IGenericRepo<Courses, String> getRepo() {
        return courseRepository;
    }

}
