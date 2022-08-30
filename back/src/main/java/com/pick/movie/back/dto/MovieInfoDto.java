package com.pick.movie.back.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;



@Builder
@Getter
public class MovieInfoDto {

    @ApiModelProperty("3")
    private long id;

    @ApiModelProperty("Avengers: Infinity War")
    private String title;

    @ApiModelProperty("The mad titan Thanos (Josh Brolin) begins his hunt for the most powerful objects in the universe, the Infinity Stones, in this movie. With the Space Stone, given by Loki (Tom Hiddleston), and the Power Stone, Thanos sends (Ebony Maw, Cull Obsidian to retrieve the Time Stone from Doctor Strange (Benedict Cumberbatch), sends Proxima Midnight, and Corvus Glaive) to retrieve the Mind Stone from Vision (Paul Bettany). Meanwhile, Tony Stark (Robert Downey, Jr.) meets up with Bruce Banner (Mark Ruffalo) and sorcerers Doctor Strange (Benedict Cumberbatch) and Wong (Benedict Wong). With help from Peter Parker/Spider-Man (Tom Holland), Stark and Strange join forces together and agree to stop Thanos. In space, Thor (Chris Hemsworth) also joins forces with the Guardians of the Galaxy, Star-Lord (Chris Pratt), Drax (Dave Bautista), Rocket (Bradley Cooper), Groot (Vin Diesel), Mantis (Pom Klementieff), and Gamora (Zoe Saldana), the daughter of Thanos who warns Thor of his power. Thor travels away to defeat Thanos with Rocket and Groot, while the other Guardians join forces with Stark, Strange, and Parker. In Wakanda, Steve Rogers (Chris Evans) and his team, Black Widow (Scarlett Johansson), Falcon (Anthony Mackie), Scarlet Witch (Elizabeth Olsen), War Machine (Don Cheadle), and Bruce Banner to protect Vision and the Mind Stone in his forehead. With protection given from King T&#39;Challa/Black Panther (Chadwick Boseman), and his Wakandan army, Thanos will come for everyone to destroy half the universe.")
    private String description;

    //평점
    @ApiModelProperty("8.4")
    private double rating;

    //런타임
    @ApiModelProperty("149")
    private int runtime;

    //영화 포스터 이미지 경로
    @ApiModelProperty("images/Avengers: Infinity War.jpg")
    private String posterPath;

    //출시연도
    @ApiModelProperty("2018")
    private int year;

}
