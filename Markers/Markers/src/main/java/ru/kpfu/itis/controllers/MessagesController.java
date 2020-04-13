package ru.kpfu.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.MessageDto;
import ru.kpfu.itis.service.message.ChatService;
import ru.kpfu.itis.service.user.UsersService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ChatService chatService;

    private static Map<Long, List<MessageDto>> messages = new HashMap<>();

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message) {

        if (!messages.containsKey(message.getUserId())) {
            List<MessageDto> messageDtoList = chatService.get(message.getUserId());
            messageDtoList.forEach(text -> text.setText(usersService.getUser(message.getUserId()).getFirstName() + " " +  usersService.getUser(message.getUserId()).getLastName() + ": " + text.getText()));
            messages.put(message.getUserId(), messageDtoList);
        }
        if (!message.getText().equals("Login")) chatService.save(message);
        message.setText(usersService.getUser(message.getUserId()).getFirstName() + " " + usersService.getUser(message.getUserId()).getLastName() + ": " +  message.getText());
        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (List<MessageDto> pageMessages : messages.values()) {
            // перед тем как положить сообщение для какой-либо страницы
            // мы список сообщений блокируем
            synchronized (pageMessages) {
                pageMessages.add(message); //TODO:Синхронизировать потоки! Сейчас они ссылаются на разные ссылки
                // говорим, что другие потоки могут воспользоваться этим списком
                pageMessages.notifyAll();
            }
        }

        return ResponseEntity.ok().build();
    }

    // получить все сообщения для текущего запроса
    @SneakyThrows
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestParam("userId") Long userId) {
        // получили список сообшений для страницы и заблокировали его
        synchronized (messages.get(userId)) {
            // если нет сообщений уходим в ожидание
            if (messages.get(userId).isEmpty()) {
                messages.get(userId).wait();
            }
        }

        // если сообщения есть - то кладем их в новых список
        List<MessageDto> response = new ArrayList<>(messages.get(userId));
        /*response.forEach(message -> message.setText(usersService.getUser(message.getUserId()).getFirstName() + " " +  usersService.getUser(message.getUserId()).getLastName() + ": " + message.getText()));*/
        // удаляем сообщения из мапы
        messages.get(userId).clear();
        return ResponseEntity.ok(response);
    }
}