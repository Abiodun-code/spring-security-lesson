package com.abioduncode.spring_security_lesson.features.authenticated.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepo userRepo;

  public PaymentController(PaymentService paymentService, UserRepo userRepo){
    this.paymentService = paymentService;
    this.userRepo = userRepo;
  }

    // Endpoint to create a payment intent
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestParam double amount) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount);
            Map<String, Object> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            // Log the error message or stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint to confirm payment after the user has entered their card details
    @PostMapping("/confirm-payment")
public ResponseEntity<String> confirmPayment(@RequestParam String paymentIntentId, @RequestParam String paymentMethodId, @RequestParam String email) {
    try {
        // Fetch the user by email
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Call the service method to confirm payment
        PaymentIntent paymentIntent = paymentService.confirmPayment(paymentIntentId, paymentMethodId, user);

        if ("succeeded".equals(paymentIntent.getStatus())) {
            return ResponseEntity.ok("Payment confirmed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment confirmation failed. Status: " + paymentIntent.getStatus());
        }
    } catch (StripeException e) { // Log the error
        // Log the error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stripe error: " + e.getMessage());
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
    }
}

}

