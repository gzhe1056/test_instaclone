package com.instacloneapi.instacloneapi.controller;

import com.instacloneapi.instacloneapi.exception.UserException;
import com.instacloneapi.instacloneapi.model.User;
import com.instacloneapi.instacloneapi.repository.UserRepository;
import com.instacloneapi.instacloneapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {

        User createdUser = userService.registerUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication authentication) throws BadCredentialsException {

        Optional<User> opt = userRepository.findByEmail(authentication.getName());

        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.ACCEPTED);
        }

        throw new BadCredentialsException("Invalid username or password");
    }

}
