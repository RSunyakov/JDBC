package ru.kpfu.itis.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.FileInfo;

import java.util.Map;

public interface FileService {
    public String saveFile(MultipartFile file, String email);
    public Resource get(String fileName);
    public FileInfo getFileInfo(String email);
}

