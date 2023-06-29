package com.instacloneapi.instacloneapi.service;

import com.instacloneapi.instacloneapi.dto.UserDto;
import com.instacloneapi.instacloneapi.exception.StoryException;
import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.Story;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.repository.StoryRepository;
import com.instacloneapi.instacloneapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService{

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public Story createStory(Story story, Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        story.setUserDto(userDto);
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

        return storyRepository.save(story);
    }


    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {

        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();

        if (stories.size() == 0) {
            throw new StoryException("This user doesn't have any sotries");
        }

        return stories;
    }

}
