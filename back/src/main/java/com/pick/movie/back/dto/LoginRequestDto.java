package com.pick.movie.back.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
