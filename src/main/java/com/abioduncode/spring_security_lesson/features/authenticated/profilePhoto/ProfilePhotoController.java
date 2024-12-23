package com.abioduncode.spring_security_lesson.features.authenticated.profilePhoto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.abioduncode.spring_security_lesson.models.Image;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile-photo")
public class ProfilePhotoController {

    private final ProfilePhotoService profilePhotoService;

    public ProfilePhotoController(ProfilePhotoService profilePhotoService) {
        this.profilePhotoService = profilePhotoService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> uploadProfilePhoto(
            @PathVariable String email, // Path variable is email
            @RequestParam("file") MultipartFile file) {
        try {
            Image image = profilePhotoService.uploadPhoto(email, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(image);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image. Please try again.");
        }
    }
}
