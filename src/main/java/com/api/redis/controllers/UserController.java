package com.api.redis.controllers;

import com.api.redis.dao.UserDao;
import com.api.redis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    // CREATE user
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userDao.save(user);
    }

    // GET single user
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userDao.get(userId);
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll()
                .values()
                .stream()
                .map(value -> (User) value)
                .collect(Collectors.toList());
    }

    // DELETE user
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userDao.delete(userId);
        return "User deleted successfully!";
    }
}
