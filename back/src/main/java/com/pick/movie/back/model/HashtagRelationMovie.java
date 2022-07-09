package com.pick.movie.back.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class HashtagRelationMovie extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "HASH_RELATION_ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID")
    private MovieInfo movieInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private TagList tag;
}
