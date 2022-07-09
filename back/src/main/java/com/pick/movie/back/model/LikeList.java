package com.pick.movie.back.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class LikeList extends BaseTimeEntity{ //찜을 담은 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIKELIST_ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID")
    private MovieInfo movieInfo;
}
