package ru.kpfu.itis.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.auditorium.AuditoriumRepository;
import ru.kpfu.itis.repositories.users.UsersRepository;
import ru.kpfu.itis.security.details.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuditoriumServiceImpl implements UserAuditoriumService {

    @Autowired
    AuditoriumRepository auditoriumRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void add(User user, Auditorium auditorium) {
        Optional<Auditorium> auditoriumOptional = auditoriumRepository.findByName(auditorium.getName());
        if (auditoriumOptional.isPresent()) {
            auditorium = auditoriumOptional.get();
            for (User auditoriumUser : auditorium.getUsers()) {
                if (auditoriumUser.getId() == user.getId()) return;
            }
            List<User> userList = auditorium.getUsers();
            userList.add(user);
            auditorium.setUsers(userList);
            List<Auditorium> auditoriumList = user.getAuditoriumList();
            auditoriumList.add(auditorium);
            user.setAuditoriumList(auditoriumList);
            auditoriumRepository.update(auditorium);
            usersRepository.update(user);
        } else {
            throw new IllegalArgumentException("Wrong auditorium name");
        }

    }
}
