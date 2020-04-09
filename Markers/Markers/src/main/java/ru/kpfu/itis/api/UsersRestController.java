package ru.kpfu.itis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.service.user.UsersService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users-management")
public class UsersRestController {
    @Autowired
    private UsersService usersService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/{user-id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId) {
        usersService.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/users/{user-id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(usersService.getUser(userId));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody SignUpDto userData) {
        return ResponseEntity.ok(usersService.addUser(userData));
    }
}
