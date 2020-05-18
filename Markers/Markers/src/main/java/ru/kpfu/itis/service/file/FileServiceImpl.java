package ru.kpfu.itis.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.FileInfo;
import ru.kpfu.itis.repositories.file.FileRepository;
import ru.kpfu.itis.repositories.users.jpa.UserRepository;

import java.io.File;
import java.io.IOException;

@Component
public class FileServiceImpl implements FileService {
    @Autowired
    private Environment environment;
    @Autowired
    private FileRepository fileRepository;
    private final int length = 40;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveFile(MultipartFile file, String email) {
        String type = "." + file.getOriginalFilename().split("\\.")[1];
        Long size = file.getSize();
        String name = file.getOriginalFilename().split("\\.")[0];
        String dbName = nameGenerate();
        try {
            file.transferTo(new File("C:\\JavaLab\\Markers\\Markers"+"\\" + dbName + type));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        ru.kpfu.itis.models.File uploadFile = ru.kpfu.itis.models.File.builder()
                .name(name)
                .db_Name(dbName)
                .size(size)
                .type(type)
                .url("localhost:8080/files/"+dbName+type)
                .emailUploader(email)
                .user(userRepository.findUserByEmail(email))
                .build();
        fileRepository.save(uploadFile);
        return dbName + type;
    }



    @Override
    public Resource get(String fileName) {
        File file = fileFor(fileName);
        Resource fileSystemResource = new FileSystemResource(file);
        return fileSystemResource;
    }

    @Override
    public FileInfo getFileInfo(String email) {
        return userRepository.getFileInfoByEmail(email);
    }

    private String nameGenerate() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
    private File fileFor(String fileName) {
        return new File("C:\\JavaLab\\Markers\\Markers", fileName);
    }
}
