package com.mingming.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
//    @JsonIgnoreProperties
//    private String password;
    private String employeeId;
    private String token;
    private String refreshToken;
}
