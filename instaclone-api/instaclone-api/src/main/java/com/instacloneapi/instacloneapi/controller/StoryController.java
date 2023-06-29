package com.instacloneapi.instacloneapi.controller;

import com.instacloneapi.instacloneapi.exception.StoryException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Story;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.service.StoryService;
import com.instacloneapi.instacloneapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin("http://localhost:3000")
public class StoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story, user.getId());

        return new ResponseEntity<Story>(createdStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {

        User user = userService.findUserById(userId);

        List<Story> stories = storyService.findStoryByUserId(userId);

        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }

}
