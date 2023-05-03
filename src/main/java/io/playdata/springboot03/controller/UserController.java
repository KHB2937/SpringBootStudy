package io.playdata.springboot03.controller;

import io.playdata.springboot03.model.User;
import io.playdata.springboot03.repository.UserRepository;
import io.playdata.springboot03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("") // http://localhost:8080/users
    public List<User> getUsers() {
//        return userRepository.findAll();
        return userService.getUsers();
    }

    @PostMapping("") // http://localhost:8080/users -> body : User
    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User createdUser = userRepository.save(user);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}") // http://localhost:8080/users/1 -> body : User
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.update(userId, user);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 값이 없음을 나타내는 응답
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
