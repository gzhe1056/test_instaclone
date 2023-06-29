package com.instacloneapi.instacloneapi.repository;

import com.instacloneapi.instacloneapi.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

    @Query("SELECT s FROM Story s WHERE s.userDto.id = :userId")
    List<Story> findAllStoryByUserId(@Param("userId") Integer userId);

}
