package com.mitocode.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitocode.Models.Roles;
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
    private String username;
    private String password;
    private Boolean status;
    private List<Roles> roles;
}
