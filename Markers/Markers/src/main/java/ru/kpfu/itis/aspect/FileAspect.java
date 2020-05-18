package ru.kpfu.itis.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.service.email.EmailService;

import java.util.concurrent.ExecutorService;

@Aspect
@Component
public class FileAspect {
    private String email;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ExecutorService executorService;

    @Pointcut("execution(* ru.kpfu.itis.service.file.FileService.saveFile(..))")
    public void selectAllMethodsAvaliable() {

    }

    @Before("selectAllMethodsAvaliable() && args(file, email)")
    public void logStringArguments(MultipartFile file, String email){
        System.out.println("String argument passed=" + email);
        this.email = email;
    }

    @AfterReturning(pointcut = "selectAllMethodsAvaliable()", returning = "someValue")
    public void afterReturningAdvice(Object someValue) {
        System.out.println("Value: " + someValue.toString());
        executorService.submit(() -> emailService.sendImageUrl(email, someValue.toString()));
    }
}

