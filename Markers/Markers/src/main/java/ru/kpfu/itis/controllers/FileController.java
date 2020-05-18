package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.models.File;
import ru.kpfu.itis.security.details.UserDetailsImpl;
import ru.kpfu.itis.service.email.EmailService;
import ru.kpfu.itis.service.file.FileService;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private EmailService emailService;


    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/storage", method = RequestMethod.GET)
    public String getStoragePage() {
        return "files";
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String fileName = fileService.saveFile(multipartFile, email);
        return ResponseEntity.ok().body(fileName);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/files/{file-name:.+}")
    public ResponseEntity<Resource> read(@PathVariable("file-name") String fileName) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileService.get(fileName));
    }
}
