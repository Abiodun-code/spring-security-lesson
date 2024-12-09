package com.abioduncode.spring_security_lesson.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.models.Demo;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class DemoController {
  private List<Demo> demo = new ArrayList<>(List.of(
    new Demo(1, "Book of life", "John doe"),
    new Demo(2, "Great Life", "Lorem Ipsum"),
    new Demo(3, "Book of life", "John doe"),
    new Demo(4, "Great Life", "Lorem Ipsum"),
    new Demo(5, "Book of life", "John doe"),
    new Demo(6, "Great Life", "Lorem Ipsum")
  ));

  
  @GetMapping("/")
  public List<Demo> geetAllDemo() {
    return demo;
    
  }

  @PostMapping("/demo")
  public ResponseEntity<Demo> postD(@RequestBody Demo postDemos) {
    demo.add(postDemos);
    return new ResponseEntity<>(postDemos, HttpStatus.CREATED);
  }
  
  @PutMapping("/demo/{id}")
public ResponseEntity<Optional<Demo>> updateDemo(@PathVariable int id, @RequestBody Demo updatedDemo) {
    Optional<Demo> existingDemo = demo.stream().filter(d -> d.getId() == id).findFirst();
    if (existingDemo.isPresent()) {
        existingDemo.get().setBookTitle(updatedDemo.getBookTitle());
        existingDemo.get().setBookAuthor(updatedDemo.getBookAuthor());
        return new ResponseEntity<>(existingDemo, HttpStatus.CREATED);
    } else {
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }
}

  @GetMapping("/{id}")
  public Optional<Demo> getDemoById(@PathVariable int id){ 

    return demo.stream().filter(c -> c.getId() == id).findFirst();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDemo(@PathVariable int id) {
    boolean isRemoved = demo.removeIf(c -> c.getId() == id);
    
    if (isRemoved) {
        return ResponseEntity.status(HttpStatus.OK).body("Demo with ID " + id +" was successfully deleted.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demo with ID " + id + " not found.");
    }
  }
  
}
