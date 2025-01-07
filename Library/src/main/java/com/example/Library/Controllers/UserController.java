package com.example.Library.Controllers;

import com.example.Library.Entities.User;
import com.example.Library.Exception.ResourceNotFoundException;
import com.example.Library.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Getting user");
        List<User>  users = (List<User>) userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        logger.info("Created a new user with an id of " + user.getId());
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        logger.info("Finding an user with an id of " + id);
        return new ResponseEntity<>(userService.getAnUserById(id),HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) throws ResourceNotFoundException {
        logger.info("Updating a user with an id of " + user.getId());
        return  userService.changeUser(user,id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        logger.info("Deleted a User with an id of " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
