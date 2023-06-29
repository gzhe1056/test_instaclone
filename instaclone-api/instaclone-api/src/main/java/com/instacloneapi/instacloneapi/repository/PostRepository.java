package com.instacloneapi.instacloneapi.repository;

import com.instacloneapi.instacloneapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.userDto.id=?1")
    List<Post> findByUserId(Integer userId);

    @Query("SELECT p FROM Post p WHERE p.userDto.id IN :users ORDER BY p.createdAt DESC")
    List<Post> findAllPostByUserIds(@Param("users") List<Integer> userIds);

}
