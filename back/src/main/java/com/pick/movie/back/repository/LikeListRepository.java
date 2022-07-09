package com.pick.movie.back.repository;

import com.pick.movie.back.model.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList,Long> {
}
