package com.pick.movie.back.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequestDto {
    @ApiModelProperty(example = "test1212@naver.com")
    private String username;
    @ApiModelProperty(example = "test1212!")
    private String password;
}
