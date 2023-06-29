package com.mingming.userservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mingming.userservice.data.RoleRepository;
import com.mingming.userservice.data.User;
import com.mingming.userservice.data.UserRepository;
import com.mingming.userservice.model.SignInForm;
import com.mingming.userservice.model.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto login(SignInForm signInForm){
        User user = userRepository.findByUsername(signInForm.getUsername());
        UserDto userDto = new UserDto();
        if(user != null){
            BeanUtils.copyProperties(user, userDto);
            if(passwordEncoder.matches(signInForm.getPassword(), user.getPassword())){
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 1000)))
                        .sign(algorithm);
                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 1000)))
                        .sign(algorithm);
                userDto.setToken(accessToken);
                userDto.setRefreshToken(refreshToken);
            }
        }
        return userDto;
    }
}
