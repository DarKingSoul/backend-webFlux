package com.mitocode.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitocode.Models.Courses;
import com.mitocode.Models.Students;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnRollDto {

    private String id;
    private Students student;
    private LocalDate dateEnroll;
    private List<Courses> courses;
    private Boolean status;

}
