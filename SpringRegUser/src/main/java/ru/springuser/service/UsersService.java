package ru.springuser.service;

import ru.springuser.dto.SignUpDto;
import ru.springuser.dto.UserDto;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();

    UserDto getUser(Long userId);

    UserDto addUser(SignUpDto userData);
}

