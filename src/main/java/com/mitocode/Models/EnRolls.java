package com.mitocode.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "enrolls")
public class EnRolls {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Field
    private Students student;
    @Field
    private LocalDate dateEnroll;
    @Field
    private List<Courses> courses;
    @Field
    private Boolean status;

}
