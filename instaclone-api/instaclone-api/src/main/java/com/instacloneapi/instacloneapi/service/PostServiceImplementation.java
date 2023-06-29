package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.dto.UserDto;
import com.instacloneapi.instacloneapi.exception.PostException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Post;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.repository.PostRepository;
import com.instacloneapi.instacloneapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.setUserDto(userDto);

        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (post.getUserDto().getId().equals(user.getId())) {
            postRepository.deleteById(post.getId());
            return "Post deleted";
        }
        throw new PostException("Cannot delete other users' posts");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {

        List<Post> posts = postRepository.findByUserId(userId);

        if (posts.size() == 0) {
            throw new UserException("This user does not have any posts");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new PostException("Post with id: " + postId + " not found");
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {

        List<Post> posts = postRepository.findAllPostByUserIds(userIds);

        if (posts.size()==0) {
            throw new PostException("No posts available");
        }

        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }

        return "Post saved";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }

        return "Post removed";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().remove(userDto);

        return postRepository.save(post);
    }
}
