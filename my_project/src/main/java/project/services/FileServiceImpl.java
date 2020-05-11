package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.repositories.FileRepository;

import java.io.File;
import java.io.IOException;

@Component
public class FileServiceImpl implements FileService {
    @Autowired
    private Environment environment;
    @Autowired
    @Qualifier(value = "fileRepositoryJpa")
    private FileRepository fileRepository;
    private final int length = 40;

    @Override
    public String save(MultipartFile file, String email) {
        String type = "." + file.getOriginalFilename().split("\\.")[1];
        Long size = file.getSize();
        String name = file.getOriginalFilename().split("\\.")[0];
        String dbName = nameGenerate();
        try {
            file.transferTo(new File(environment.getProperty("storage.path")+"\\" + dbName + type));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        fileRepository.save(project.models.File.builder()
                            .name(name)
                            .db_Name(dbName)
                            .size(size)
                            .type(type)
                            .url("localhost:8080/files/"+dbName+type)
                            .build());
        System.out.println("sss" + email);
        return dbName + type;
    }

    @Override
    public Resource get(String fileName) {
        File file = fileFor(fileName);
        Resource fileSystemResource = new FileSystemResource(file);
        return fileSystemResource;
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
        return new File(environment.getProperty("storage.path"), fileName);
    }
}
