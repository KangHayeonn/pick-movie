package com.pick.movie.back.dto;

import lombok.Data;

import java.util.List;

@Data
public class SignupDto {
    private String username;
    private String password;
    private List<String> tags;
}