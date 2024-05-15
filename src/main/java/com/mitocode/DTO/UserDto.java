package com.mitocode.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitocode.Models.Roles;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String id;
    @NotNull
    @Size(min = 8, message = "El nombre de usuario debe tener al menos 3 caracteres")
    private String username;
    @NotNull
    private String password;
    private Boolean status;
    private List<Roles> roles;
}
