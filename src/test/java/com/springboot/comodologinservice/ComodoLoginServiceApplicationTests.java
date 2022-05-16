package com.springboot.comodologinservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.springboot.comodologinservice.model.TodoUserIO;
import com.springboot.comodologinservice.service.UserService;

@SpringBootTest
class ComodoLoginServiceApplicationTests {

  @Autowired
  private UserService userservice;

  @Test
  void shouldSaveUser() {

    TodoUserIO userIO = new TodoUserIO();
    userIO.setName("Sarah");
    userIO.setSurname("Hays");
    userIO.setEmail("sarah_hays@gmail.com");
    userIO.setPassword("password123");

    TodoUserIO savedUserIO = userservice.saveUser(userIO);

    assertAll(() -> assertEquals("Sarah", savedUserIO.getName()),
        () -> assertEquals("Hays", savedUserIO.getSurname()),
        () -> assertEquals("sarah_hays@gmail.com", savedUserIO.getEmail()),
        () -> assertEquals("password123", savedUserIO.getPassword()));
  }

  @Test
  void shouldUpdateUser() {

    TodoUserIO userIO = new TodoUserIO();
    userIO.setName("Jack");
    userIO.setSurname("Jill");
    userIO.setEmail("jack_jill@gmail.com");
    userIO.setPassword("password123");

    TodoUserIO savedUserIO = userservice.saveUser(userIO);

    savedUserIO.setEmail("updated_email@gmail.com");
    savedUserIO.setName("John");
    savedUserIO.setSurname("Brown");
    savedUserIO.setPassword("password456");

    TodoUserIO updatedUserIO = userservice.updateUser(savedUserIO.getId(), savedUserIO);

    assertAll(() -> assertEquals("John", updatedUserIO.getName()),
        () -> assertEquals("Brown", updatedUserIO.getSurname()),
        () -> assertEquals("updated_email@gmail.com", updatedUserIO.getEmail()),
        () -> assertEquals("password456", updatedUserIO.getPassword()));
  }

  @Test
  void shouldGetUserByEmailAndPassword() {
    TodoUserIO userIO = new TodoUserIO();
    userIO.setName("Jack");
    userIO.setSurname("Jill");
    userIO.setEmail("jack_jill@gmail.com");
    userIO.setPassword("password123");

    TodoUserIO savedUserIO = userservice.saveUser(userIO);

    TodoUserIO checkedUserIO = userservice.getUserByEmail("jack_jill@gmail.com");

    assertAll(() -> assertEquals("Jack", checkedUserIO.getName()),
        () -> assertEquals("Jill", checkedUserIO.getSurname()),
        () -> assertEquals("jack_jill@gmail.com", checkedUserIO.getEmail()),
        () -> assertEquals(savedUserIO.getId(), checkedUserIO.getId()),
        () -> assertEquals("password123", checkedUserIO.getPassword()));

  }

}
