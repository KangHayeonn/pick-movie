package com.pick.movie.back.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class MovieInfo extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MOVIE_ID")
    private long id;

    @OneToMany(mappedBy = "movieInfo")  //해시태그 연결관계
    private List<HashtagRelationMovie> hashtagRelationMovies = new ArrayList<HashtagRelationMovie>();

    @OneToMany(mappedBy = "movieInfo")      //좋아요 연결관계
    private List<LikeList> likeLists = new ArrayList<LikeList>();

    @OneToMany(mappedBy = "movieInfo")      //시청목록 연결관계
    private List<Watching> watchings = new ArrayList<Watching>();

    //영화 제목.
    @Column(unique = true)
    private String title;

    //줄거리
    @Column(length = 5000)
    private String description;

    //평점
    private double rating;

    //런타임
    private int runtime;

    //영화 포스터 이미지 경로
    @Column(unique = true)
    private String posterPath;

    //출시연도
    private int year;
}
