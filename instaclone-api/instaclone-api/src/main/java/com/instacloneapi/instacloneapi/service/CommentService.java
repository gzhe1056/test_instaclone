package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.exception.CommentException;
import com.instacloneapi.instacloneapi.exception.PostException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Comment;

public interface CommentService {

    Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;
    Comment findCommentById(Integer commentId) throws CommentException;
    Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException;
    Comment unLikeComment(Integer commentId, Integer userId) throws CommentException, UserException;

}
