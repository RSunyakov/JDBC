package ru.springuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.springuser.dto.SignUpDto;
import ru.springuser.dto.api.ResponseUserDto;
import ru.springuser.dto.api.ResponseUsersDto;
import ru.springuser.service.UsersService;

@RestController
@RequestMapping("/api/users-management")
public class UsersRestController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseUsersDto getUsers() {
        return ResponseUsersDto.builder()
                .data(usersService.getAllUsers())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/{user-id}", method = RequestMethod.GET)
    public ResponseUserDto getUser(@PathVariable("user-id") Long userId) {
        return ResponseUserDto.builder()
                .data(usersService.getUser(userId))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseUserDto addUser(@RequestBody SignUpDto userData) {
        return ResponseUserDto.builder()
                .data(usersService.addUser(userData))
                .build();
    }


}
