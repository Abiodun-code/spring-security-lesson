package com.abioduncode.spring_security_lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abioduncode.spring_security_lesson.models.Image;

public interface ImageRepo extends JpaRepository<Image, Integer> {
  Image findByUserId(Integer userId);
}
