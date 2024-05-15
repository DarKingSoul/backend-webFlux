package com.mitocode.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "menus")
public class Menus {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Field
    private String icon;
    @Field
    private String name;
    @Field
    private Boolean url;
    @Field
    private List<Roles> roles;

}
