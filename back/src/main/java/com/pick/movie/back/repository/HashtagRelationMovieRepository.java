package com.pick.movie.back.repository;

import com.pick.movie.back.model.HashtagRelationMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRelationMovieRepository extends JpaRepository<HashtagRelationMovie,Long> {

}
