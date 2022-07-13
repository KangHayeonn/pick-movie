package com.pick.movie.back.model;

import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private long id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "user")
    private List<HashtagRelationUser> hashtagRelation = new ArrayList<HashtagRelationUser>();

    @OneToMany(mappedBy = "user")
    private List<LikeList> likeLists = new ArrayList<LikeList>();


    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}