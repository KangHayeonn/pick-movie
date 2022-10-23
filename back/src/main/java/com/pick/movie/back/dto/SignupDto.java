package com.pick.movie.back.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SignupDto {

    @ApiModelProperty(example = "test1212@naver.com")
    private String username;
    @ApiModelProperty(example = "test1212!")
    private String password;
    private List<String> tags;
}