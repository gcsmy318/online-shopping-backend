package com.example.shopping.controller;

import com.example.shopping.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final EmailService emailService;

    public OrderController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> orderData) {
        String fixedEmail = "ilamoonshopping@gmail.com"; // ✅ Fix อีเมลให้ส่งไปยังเมลล์นี้เสมอ
        emailService.sendOrderConfirmation(fixedEmail, orderData);
        return ResponseEntity.ok("✅ สั่งซื้อสำเร็จ & อีเมลถูกส่งไปยัง ilamoonshopping@gmail.com");
    }
}
