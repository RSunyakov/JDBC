package ru.kpfu.itis.javalabmessagequeue.controllers;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.LinkedMultiValueMap;
import ru.kpfu.itis.javalabmessagequeue.models.MessageBody;
import ru.kpfu.itis.javalabmessagequeue.models.Message;
import ru.kpfu.itis.javalabmessagequeue.models.Task;
import ru.kpfu.itis.javalabmessagequeue.service.MessageService;
import ru.kpfu.itis.javalabmessagequeue.service.TaskService;

import java.util.*;

@Controller
public class JlmqController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;

    @MessageMapping("/producer")
    public Message receiveMessageFromProducer(org.springframework.messaging.Message<MessageBody> message) {
        Task task = Task.builder().queueName((String) ((LinkedMultiValueMap<String, Object>) message.getHeaders().get("nativeHeaders")).get("queueName").get(0)).state("NOT ASSIGNED").build();
        Message tMessage = Message.builder().messageId(UUID.randomUUID().toString()).state("NOT ACCEPTED").task(task).build();
        MessageBody messageBody = MessageBody.builder().body(message.getPayload().getBody()).message(tMessage).build();
        tMessage.setMessageBody(messageBody);
        List<Message> messages = new ArrayList<>();
        messages.add(tMessage);
        task.setMessage(messages);
        taskService.saveTask(task);
        messageService.saveMessage(tMessage);
        return tMessage;
    }

    @SubscribeMapping("/consumer_sent")
    public Message receiveMessageFromConsumer(SimpMessageHeaderAccessor headerAccessor) {
        taskService.updateTask(headerAccessor.getFirstNativeHeader("queueName"), "CONSUMER SUBSCRIBED");
        Task task = taskService.getTask(headerAccessor.getFirstNativeHeader("queueName"));
        return task.getMessage().get(0);
    }

    @MessageMapping("/producer_sent")
    @SendTo("/jlmq/consumer_sent")
    public Message sendMessage(org.springframework.messaging.Message<MessageBody> message) {
        String queueName = (String) ((LinkedMultiValueMap<String, Object>) message.getHeaders().get("nativeHeaders")).get("queueName").get(0);
        Message tMessage = Message.builder().task(taskService.getTask(queueName)).state("NOT ACCEPTED").messageId(UUID.randomUUID().toString()).messageBody(message.getPayload()).build();
        messageService.saveMessage(tMessage);
        return tMessage;
    }

    @MessageMapping("/updateMessage")
    public void updateMessage(org.springframework.messaging.Message<Message> message) {
        messageService.updateMessage(message.getPayload());
    }

}
