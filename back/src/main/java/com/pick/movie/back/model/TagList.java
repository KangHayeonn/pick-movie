package com.pick.movie.back.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
public class TagList extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TAG_ID")
    private long id;

    private String tag;
}
