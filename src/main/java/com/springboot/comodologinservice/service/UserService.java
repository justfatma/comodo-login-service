package com.springboot.comodologinservice.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.springboot.comodologinservice.model.TodoUser;
import com.springboot.comodologinservice.model.TodoUserIO;
import com.springboot.comodologinservice.repository.UserRepository;

@Service
public class UserService {


  @Autowired
  private UserRepository userRepository;


  public TodoUserIO getUserByEmail(String email) {
    List<TodoUser> list = userRepository.findByEmail(email);

    if (list.isEmpty()) {
      return null;
    } else {
      return fromEntityToModel(list.get(0));
    }
  }

  public List<TodoUserIO> getUsers() {
    List<TodoUserIO> userIOList = new ArrayList<>();
    List<TodoUser> list = userRepository.findAll();

    for (TodoUser user : list) {
      userIOList.add(fromEntityToModel(user));
    }

    return userIOList;
  }

  public TodoUserIO saveUser(TodoUserIO model) {

    List<TodoUser> list = userRepository.findByEmail(model.getEmail());
    if (!list.isEmpty()) {
      return null;
    } else {
      TodoUser user = fromModelToEntity(model);
      TodoUser savedUser = userRepository.save(user);
      return fromEntityToModel(savedUser);
    }

  }


  public TodoUserIO updateUser(Long id, TodoUserIO userIO) {

    TodoUser user = fromModelToEntity(userIO);

    TodoUser currentUser =
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
            "UserService/updateUser : User Id not found. -> " + id));

    currentUser.setEmail(user.getEmail());
    currentUser.setName(user.getName());
    currentUser.setPassword(user.getPassword());
    currentUser.setSurname(user.getSurname());

    TodoUser savedUser = userRepository.save(currentUser);
    return fromEntityToModel(savedUser);

  }


  private TodoUser fromModelToEntity(TodoUserIO userIO) {

    TodoUser user = new TodoUser();
    user.setId(userIO.getId());
    user.setName(userIO.getName());
    user.setEmail(userIO.getEmail());
    user.setPassword(userIO.getPassword());
    user.setSurname(userIO.getSurname());

    return user;
  }

  private TodoUserIO fromEntityToModel(TodoUser user) {

    TodoUserIO userIO = new TodoUserIO();
    userIO.setId(user.getId());
    userIO.setName(user.getName());
    userIO.setEmail(user.getEmail());
    userIO.setPassword(user.getPassword());
    userIO.setSurname(user.getSurname());

    return userIO;
  }



}
