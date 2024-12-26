package com.abioduncode.spring_security_lesson.features.authenticated.payment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.Payment;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.PaymentRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
    }

    @Transactional // Ensures the User is attached to the persistence context
    public PaymentIntent createPaymentIntent(double amount, String email) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        // Retrieve the user from the database
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CustomException("User with email " + email + " not found"));

        // Create PaymentIntent on Stripe
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) // Convert amount to cents
                .setCurrency("usd")
                .putMetadata("user_email", user.getEmail()) // Add metadata for traceability
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        // Save the Payment entity and associate it with the User
        Payment payment = new Payment();
        payment.setUser(user); // User is attached because of @Transactional
        payment.setAmount(amount);
        payment.setType("Deposit");
        payment.setTimeStamp(new Date());
        paymentRepo.save(payment);

        // Update the user's balance
        user.setBalance(user.getBalance() == null ? amount : user.getBalance() + amount);
        userRepo.save(user);

        return paymentIntent;
    }
}
