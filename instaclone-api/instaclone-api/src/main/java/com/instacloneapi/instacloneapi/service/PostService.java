package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.exception.PostException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post, Integer userId) throws UserException;
    String deletePost(Integer postId, Integer userId) throws UserException, PostException;
    List<Post> findPostByUserId(Integer userId) throws UserException;
    Post findPostById(Integer postId) throws PostException;
    List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException;
    String savedPost(Integer postId, Integer userId) throws PostException, UserException;
    String unSavedPost(Integer postId, Integer userId) throws PostException, UserException;
    Post likePost(Integer postId, Integer userId) throws UserException, PostException;
    Post unLikePost(Integer postId, Integer userId) throws UserException, PostException;
}
