package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.dto.UserDto;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.repository.UserRepository;
import com.instacloneapi.instacloneapi.security.JwtTokenClaims;
import com.instacloneapi.instacloneapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());

        if (isEmailExist.isPresent()) {
            throw new UserException("Email already exists");
        }
        if (isUsernameExist.isPresent()) {
            throw new UserException("Username taken");
        }
        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null || user.getName() == null) {
            throw new UserException("All fields are required");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {

        Optional<User> opt = userRepository.findById(userId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User with ID: " + userId + "does not exist");
    }

    @Override
    public User findUserProfile(String token) throws UserException {

        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);

        String email = jwtTokenClaims.getUsername();

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("Invalid token");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User with username: " + username + "does not exist");
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();

        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setUserImage(followUser.getImage());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You are following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();

        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setUserImage(followUser.getImage());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());

        reqUser.getFollowing().remove(follower);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You have unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {

        List<User> users = userRepository.findAllUsersByUserId(userIds);

        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {

        List<User> users = userRepository.findByQuery(query);

        if (users.size() == 0) {
            throw new UserException("User not found");
        }

        return users;
    }

    @Override
    public User updateUserDetails(User updateUser, User existingUser) throws UserException {

        if (updateUser.getEmail() != null) {
            existingUser.setEmail(updateUser.getEmail());
        }
        if (updateUser.getPassword() != null) {
            existingUser.setPassword(updateUser.getPassword());
        }
        if (updateUser.getUsername()!=null) {
            existingUser.setUsername(updateUser.getUsername());
        }
        if (updateUser.getName()!=null) {
            existingUser.setName(updateUser.getName());
        }
        if (updateUser.getBio()!=null) {
            existingUser.setBio(updateUser.getBio());
        }
        if (updateUser.getMobile()!=null) {
            existingUser.setMobile(updateUser.getMobile());
        }
        if (updateUser.getGender()!=null) {
            existingUser.setGender(updateUser.getGender());
        }
        if (updateUser.getWebsite()!=null) {
            existingUser.setWebsite(updateUser.getWebsite());
        }
        if (updateUser.getImage()!=null) {
            existingUser.setImage(updateUser.getImage());
        }
        if (updateUser.getId().equals(existingUser.getId())) {
            return userRepository.save(existingUser);
        }
        throw new UserException("Error. Cannot update user");
    }
}
