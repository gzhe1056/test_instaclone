package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.User;

import java.util.List;

public interface UserService {

    User registerUser(User user) throws UserException;
    User findUserById(Integer userId) throws UserException;
    User findUserProfile(String token) throws UserException;
    User findUserByUsername(String username) throws UserException;
    String followUser(Integer reqUserId, Integer followUserId) throws UserException;
    String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException;
    List<User> findUserByIds(List<Integer> userIds) throws UserException;
    List<User> searchUser(String query) throws UserException;
    User updateUserDetails(User updateUser, User existingUser) throws UserException;

}
