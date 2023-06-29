package com.instacloneapi.instacloneapi.controller;

import com.instacloneapi.instacloneapi.exception.PostException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Post;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.response.MessageResponse;
import com.instacloneapi.instacloneapi.service.PostService;
import com.instacloneapi.instacloneapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("http://localhost:3000/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());

        return new ResponseEntity<Post>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("id") Integer userId) throws UserException {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable("ids") List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postService.findAllPostByUserIds(userIds);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException {

        Post post = postService.findPostById(postId);

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());

        return new ResponseEntity<Post>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post unLikedPost = postService.unLikePost(postId, user.getId());

        return new ResponseEntity<Post>(unLikedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);

        String message = postService.deletePost(postId, user.getId());

        MessageResponse response = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        String message = postService.savedPost(postId, user.getId());

        MessageResponse response = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        String message = postService.unSavedPost(postId, user.getId());

        MessageResponse response = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(response, HttpStatus.ACCEPTED);
    }


}
