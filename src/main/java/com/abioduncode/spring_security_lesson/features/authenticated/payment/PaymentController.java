package com.abioduncode.spring_security_lesson.features.authenticated.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abioduncode.spring_security_lesson.repository.UserRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Endpoint to create a payment intent
    @PostMapping("/{email}/create-payment")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(
            @PathVariable String email, 
            @RequestParam double amount) throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, email);
        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());
        response.put("email", email);
        response.put("amount", amount);
        return ResponseEntity.ok(response);
    }
}
