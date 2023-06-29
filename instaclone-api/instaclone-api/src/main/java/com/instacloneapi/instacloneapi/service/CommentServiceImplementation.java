package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.dto.UserDto;
import com.instacloneapi.instacloneapi.exception.CommentException;
import com.instacloneapi.instacloneapi.exception.PostException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Comment;
import com.instacloneapi.instacloneapi.model.Post;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.repository.CommentRepository;
import com.instacloneapi.instacloneapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {

        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.setUserDto(userDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);

        postRepository.save(post);

        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {

        Optional<Comment> opt = commentRepository.findById(commentId);
        if (opt.isPresent()) {
            return opt.get();
        }

        throw new CommentException("Comment with id: " + commentId + " does not exist");
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().add(userDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment unLikeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().remove(userDto);

        return commentRepository.save(comment);
    }
}
