package com.pick.movie.back.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LikeListRequestDto {

    @ApiModelProperty("2")
    long userId;

    @ApiModelProperty("124")
    long movieId;

}
