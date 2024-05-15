package com.mitocode.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentsDto {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    @Size(min = 10, max = 13)
    private String dni;
    private LocalDate birthDate;
    @Email
    private String email;
    @Min(12)
    private Integer age;
    private String urlPhoto;

}
