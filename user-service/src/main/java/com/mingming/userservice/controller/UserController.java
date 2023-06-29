package com.mingming.userservice.controller;

import com.mingming.userservice.data.User;
import com.mingming.userservice.model.SignInForm;
import com.mingming.userservice.model.UserDto;
import com.mingming.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list-users")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody User user){
        userService.saveUser(user);
        return "Created user successful!";
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody SignInForm signInForm){
        return userService.login(signInForm);
    }
}
