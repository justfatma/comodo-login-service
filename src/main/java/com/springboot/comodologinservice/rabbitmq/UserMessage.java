package com.springboot.comodologinservice.rabbitmq;

import java.util.Date;
import com.springboot.comodologinservice.model.TodoUserIO;

public class UserMessage {

  private String messageId;
  private Date messageDate;

  private TodoUserIO todoUserIO;

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public Date getMessageDate() {
    return messageDate;
  }

  public void setMessageDate(Date messageDate) {
    this.messageDate = messageDate;
  }

  public TodoUserIO getTodoUserIO() {
    return todoUserIO;
  }

  public void setTodoUserIO(TodoUserIO todoUserIO) {
    this.todoUserIO = todoUserIO;
  }

  @Override
  public String toString() {
    return "UserMessage [messageId=" + messageId + ", messageDate=" + messageDate + ", todoUserIO="
        + todoUserIO + "]";
  }

}
