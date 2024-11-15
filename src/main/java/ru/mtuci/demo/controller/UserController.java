package ru.mtuci.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public  UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('read')")
    public List<User> findAll() { return userService.findAll(); }

    @PostMapping("/saveuser")
    @PreAuthorize("hasAnyAuthority('modification')")
    public void save(@RequestBody User user) { userService.save(user); }
}