package com.mitocode.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "students")
public class Students {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Field
    private String name;
    @Field
    private String lastName;
    @Field
    private String dni;
    @Field
    private LocalDate birthDate;
    @Field
    private String email;
    @Field
    private Integer age;
    @Field
    private String urlPhoto;

}
