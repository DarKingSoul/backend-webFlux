package com.mitocode.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "courses")
public class Courses {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Field
    private String name;
    @Field
    private String signals;
    @Field
    private Boolean status;

}
