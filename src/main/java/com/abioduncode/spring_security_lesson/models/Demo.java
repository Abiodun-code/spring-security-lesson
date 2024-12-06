package com.abioduncode.spring_security_lesson.models;

import java.util.Date;

public class Demo {
  private int id;
  private String bookTitle;
  private String bookAuthor;
  private Date createdAt;


  public Demo(int id, String bookTitle, String bookAuthor) {
    //TODO Auto-generated constructor stub
    this.id = id;
    this.bookTitle = bookTitle;
    this.bookAuthor = bookAuthor;
    this.createdAt = new Date();
  }

  public int getId() {
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public String getBookAuthor() {
    return bookAuthor;
  }

  public void setBookAuthor(String bookAuthor){
    this.bookAuthor = bookAuthor;
  }

  public Date getCreatedAt(){
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Demo{" +
            "id=" + id +
            ", bookTitle='" + bookTitle + '\'' +
            ", bookAuthor='" + bookAuthor + '\'' +
            ", createdAt=" + createdAt +
            '}';
  }
}
