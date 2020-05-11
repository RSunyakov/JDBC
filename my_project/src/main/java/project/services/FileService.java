package project.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String save(MultipartFile file, String email);
    public Resource get(String fileName);
}
