package ru.kpfu.itis.repositories.users.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.dto.FileInfo;
import ru.kpfu.itis.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    @Query("select new ru.kpfu.itis.dto.FileInfo(user.email, count(files.id)) from User user left join user.files as files where files.emailUploader=:userEmail group by user.email")
    FileInfo getFileInfoByEmail(@Param("userEmail")String userEmail);
}
