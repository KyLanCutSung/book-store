package com.mingming.userservice.services;

import com.mingming.userservice.data.User;
import com.mingming.userservice.model.SignInForm;
import com.mingming.userservice.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUser();
    void saveUser(User user);
    UserDto login(SignInForm signInForm);
}
