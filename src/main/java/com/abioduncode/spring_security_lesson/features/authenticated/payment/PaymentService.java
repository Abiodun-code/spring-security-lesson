package com.abioduncode.spring_security_lesson.features.authenticated.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.models.Payment;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.PaymentRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;

    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
    }

    @Value("${stripe.secret.key}")
    private String StripeSecretKey;

    // Creates a payment intent on Stripe
    public PaymentIntent createPaymentIntent(double amount) throws StripeException {
        Stripe.apiKey = StripeSecretKey;
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) // Convert amount to cents
                .setCurrency("usd")
                .build();

        return PaymentIntent.create(createParams);
    }

    // Confirms the payment with the payment method
    public PaymentIntent confirmPayment(String paymentIntentId, String paymentMethodId, User user) throws StripeException {
    Stripe.apiKey = StripeSecretKey;

    // Retrieve the existing PaymentIntent from Stripe
    PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

    // Confirm the PaymentIntent with the payment method
    Map<String, Object> confirmParams = new HashMap<>();
    confirmParams.put("payment_method", paymentMethodId);

    paymentIntent = paymentIntent.confirm(confirmParams);

    // Process the payment if the confirmation is successful
    if ("succeeded".equals(paymentIntent.getStatus())) {
        processPayment(paymentIntent, user, paymentIntent.getAmount() / 100.0); // Convert cents to dollars
    }

    return paymentIntent;
}

    // Process the payment, update user's balance and save the transaction
    public void processPayment(PaymentIntent paymentIntent, User user, double amount) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(amount);
        payment.setType("Deposit");  // This could be dynamic based on the payment flow
        payment.setTimeStamp(new Date());

        // Update user's balance based on the payment amount
        user.setBalance(user.getBalance() + amount);  // For deposit, increase the balance
        paymentRepo.save(payment);
        userRepo.save(user);
    }
}
