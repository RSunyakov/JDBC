package project.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.dto.MessageDto;
import project.dto.UserDto;
import project.executor.ExecutorServiceImpl;
import project.models.Message;
import project.models.User;
import project.services.MessageService;
import project.services.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;

@RestController
public class MessagesRestController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExecutorServiceImpl executorService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/messages")
    public ResponseEntity<Object> sendMessage(@RequestBody MessageDto message) {
        messageService.save(Message.builder()
                .id_from(message.getUserId())
                .id_to(message.getReceiverId())
                .text(message.getText())
                .delivered(false)
                .send(LocalDateTime.now())
                .build());
        executorService.notifyUser(message.getReceiverId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/messages/start")
    public ResponseEntity<List<MessageDto>> start(@RequestParam("userId") Long userId) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messageService.makeAllMessagesFromUserDelivered(userId);
        for (Message m : messageService.findAllForUser(userId)
        ) {
            messageDtos.add(MessageDto.fromMessage(m));
        }
        return ResponseEntity.ok(messageDtos);
    }

    @PreAuthorize("isAuthenticated()")
    @SneakyThrows
    @GetMapping("/api/messages")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestParam("userId") Long userId) {
        List<MessageDto> response = new ArrayList<>();
        executorService.submit(userId, new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if (messageService.getAllNotDeliveredForUser(userId).isEmpty()) {
                    synchronized (this) {
                        this.wait();
                    }
                }
                for (Message m : messageService.getAllNotDeliveredForUser(userId)
                ) {
                    response.add(MessageDto.fromMessage(m));
                }
                messageService.makeAllMessagesToUserDelivered(userId);
                synchronized (response) {
                    response.notify();
                }
                executorService.clear(userId);
            }
        });

        synchronized (response) {
            while (response.isEmpty()) {
                response.wait();
            }
        }

        return ResponseEntity.ok(response);
    }


    @SneakyThrows
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/admin/support")
    public ResponseEntity<List<MessageDto>> getMessagesForAdminPage(@RequestParam("userId") Long userId) {

        List<MessageDto> response = new ArrayList<>();
        executorService.submit(0L, new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if (messageService.getAllNotDeliveredFromUser(userId).isEmpty()) {
                    synchronized (this) {
                        this.wait();
                    }
                }
                for (Message m : messageService.getAllNotDeliveredFromUser(userId)
                ) {
                    response.add(MessageDto.fromMessage(m));
                }
                messageService.makeAllMessagesFromUserDelivered(userId);
                synchronized (response) {
                    response.notify();
                }
                executorService.clear(0L);
            }
        });
        synchronized (response) {
            while (response.isEmpty()) {
                response.wait();
            }
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/admin/support/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> response = new ArrayList<>();
        for (Long id : messageService.getUsers()
        ) {
            Optional<User> userCandidate = userService.find(id);
            if (userCandidate.isPresent()) {
                response.add(UserDto.builder()
                        .id(id)
                        .email(userCandidate.get().getEmail())
                        .build());
            }
        }
        return ResponseEntity.ok(response);
    }
}

