package com.abioduncode.spring_security_lesson.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="profile_image")
public class Image{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer imId;

  private String imageUrl;

  private String publicId;

  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private User user;

  public Image(){}

  @Override
  public String toString (){
    return "Image{" +
        "imId=" + imId +
        ", iimageUrl=" + imageUrl +
        ", publicId=" + publicId +
        ", user=" + user +
        '}';
  }
}