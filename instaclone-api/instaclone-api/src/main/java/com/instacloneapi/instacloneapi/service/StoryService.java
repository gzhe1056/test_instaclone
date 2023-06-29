package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.exception.StoryException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Story;

import java.util.List;

public interface StoryService {

    Story createStory(Story story, Integer userId) throws UserException;

    List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;

}
