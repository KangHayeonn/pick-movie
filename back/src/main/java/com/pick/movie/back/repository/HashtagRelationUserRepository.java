package com.pick.movie.back.repository;

import com.pick.movie.back.model.HashtagRelationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRelationUserRepository extends JpaRepository<HashtagRelationUser,Long> {

}
