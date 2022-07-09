package com.pick.movie.back.repository;

import com.pick.movie.back.model.TagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagList,Long> {
    TagList findByTag(String tag);
}
