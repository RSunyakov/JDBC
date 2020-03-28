package ru.springuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.springuser.exception.NotFoundException;
import ru.springuser.model.User;
import ru.springuser.repositories.UsersRepository;

import java.util.List;
@Service
public class ListUserServiceImpl implements ListUserService {
    @Autowired
    UsersRepository repository;


    @Override
    public List<User> getAllUsers() {
       return repository.findAll();
    }
}
