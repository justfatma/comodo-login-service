package com.springboot.comodologinservice.controller;

import java.util.Date;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.comodologinservice.model.TodoUserIO;
import com.springboot.comodologinservice.rabbitmq.MQConfig;
import com.springboot.comodologinservice.rabbitmq.UserMessage;
import com.springboot.comodologinservice.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RabbitTemplate template;

  @GetMapping("user/{email}/{password}")
  public ResponseEntity<TodoUserIO> getUserByEmailAndPassword(@PathVariable String email,
      @PathVariable String password) {

    return new ResponseEntity<>(userService.getUserByEmailAndPassword(email, password),
        HttpStatus.OK);
  }

  @PostMapping("user")
  public ResponseEntity<TodoUserIO> saveUser(@RequestBody TodoUserIO userIO) {

    TodoUserIO user = userService.saveUser(userIO);

    if (user != null) {
      // ***************************************************//
      publishMessage(user);
      // ***************************************************//
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }


  @PutMapping("user/{userId}")
  public ResponseEntity<TodoUserIO> updateUser(@PathVariable Long userId,
      @RequestBody TodoUserIO todoUserIO) {

    TodoUserIO user = userService.updateUser(userId, todoUserIO);
    if (user != null) {
      // ***************************************************//
      publishMessage(user);
      // ***************************************************//
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  public void publishMessage(TodoUserIO todoUserIO) {
    UserMessage userMessage = new UserMessage();
    userMessage.setTodoUserIO(todoUserIO);
    userMessage.setMessageId(UUID.randomUUID().toString());
    userMessage.setMessageDate(new Date());
    template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, userMessage);
  }

}
