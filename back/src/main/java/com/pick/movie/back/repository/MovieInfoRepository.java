package com.pick.movie.back.repository;

import com.pick.movie.back.model.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo,Long> {
    MovieInfo findByTitle(String title);
    List<MovieInfo> findByTitleContains(String title);
}
