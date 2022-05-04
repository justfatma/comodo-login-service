package com.springboot.comodologinservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.comodologinservice.model.TodoUser;

@Repository
public interface UserRepository extends JpaRepository<TodoUser, Long> {

  public List<TodoUser> findByEmailAndPassword(String email, String password);

  public List<TodoUser> findByEmail(String email);
}
