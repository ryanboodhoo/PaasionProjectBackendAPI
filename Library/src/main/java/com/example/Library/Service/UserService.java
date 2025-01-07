package com.example.Library.Service;

import com.example.Library.Entities.User;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        for(User user : userRepository.findAll()){
            userList.add(user);
        }
        return userList;
    }

    //create a user
    public void addUser(User user){

        userRepository.save(user);
    }
    //DELETE user
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User getAnUserById(Long UserId) {

        User user = userRepository.findById(UserId).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User with id of " + user + " not found");
        }
        return user;
    }

    //Update a user
    public ResponseEntity<?> changeUser(User user, Long id) {

        if (userRepository.findById(id).isPresent()) {
            User newUser = userRepository.findById(id).get();
            newUser.getName();
            return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.ACCEPTED);
        }
        throw new ResourceNotFoundException("User with id of " + id + " not found");
    }
}
