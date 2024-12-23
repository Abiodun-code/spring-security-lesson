package com.abioduncode.spring_security_lesson.features.authenticated.profilePhoto;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.abioduncode.spring_security_lesson.models.Image;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.ImageRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
@Transactional // Ensures all operations occur within the same transaction
public class ProfilePhotoService {

    private final Cloudinary cloudinary;
    private final ImageRepo imageRepo;
    private final UserRepo userRepo;

    public ProfilePhotoService(Cloudinary cloudinary, ImageRepo imageRepo, UserRepo userRepo) {
        this.cloudinary = cloudinary;
        this.imageRepo = imageRepo;
        this.userRepo = userRepo;
    }

    public Image uploadPhoto(String email, MultipartFile file) throws IOException {
    // Fetch the user by email
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

    // Upload file to Cloudinary
    @SuppressWarnings("unchecked")
    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

    if (!uploadResult.containsKey("public_id") || !uploadResult.containsKey("url")) {
        throw new RuntimeException("Failed to upload file to Cloudinary.");
    }

    // Check if the user already has an image
    Image image = user.getImage();
    if (image == null) {
        image = new Image();
        image.setUser(user); // Set the relationship
    }

    // Update image fields
    image.setPublicId(uploadResult.get("public_id").toString());
    image.setImageUrl(uploadResult.get("url").toString());

    // Save the image (and cascade the user relationship if necessary)
    image = imageRepo.save(image);

    // Update the user's image association
    user.setImage(image);
    userRepo.save(user); // Save the user (optional, for bidirectional sync)

    return image;
}


}
