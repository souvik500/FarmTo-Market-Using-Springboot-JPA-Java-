package com.dnapass.training.service;

import com.dnapass.training.entity.User;
import com.dnapass.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;


    //To add new user
    public User saveUser(User user) {
        return userRepository.save(user);

    }

    //To find user based on user id
    public User findUser(Integer userId) {

        return userRepository.findById(userId).orElse(null);

    }

}

