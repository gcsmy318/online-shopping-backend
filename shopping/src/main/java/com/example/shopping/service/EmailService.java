package com.example.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, Map<String, Object> orderData) {
        try {
            String subject = "📦 มีคำสั่งซื้อใหม่จาก Ilamoon";
            String body = buildEmailContent(orderData);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // ✅ ใช้ HTML ในอีเมล

            mailSender.send(message);
            System.out.println("✅ อีเมลถูกส่งไปยัง: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("❌ ส่งอีเมลล้มเหลว: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buildEmailContent(Map<String, Object> orderData) {
        if (orderData == null || !orderData.containsKey("cart")) {
            return "<p>❌ ไม่มีข้อมูลคำสั่งซื้อ</p>";
        }

        String name = (String) orderData.getOrDefault("name", "ไม่ระบุ");
        String address = (String) orderData.getOrDefault("address", "ไม่ระบุ");
        String phone = (String) orderData.getOrDefault("phone", "ไม่ระบุ");

        List<Map<String, Object>> cart = (List<Map<String, Object>>) orderData.get("cart");

        double totalPrice = 0.0;
        if (orderData.get("totalPrice") instanceof Number) {
            totalPrice = ((Number) orderData.get("totalPrice")).doubleValue();
        }

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<h2>📦 ข้อมูลคำสั่งซื้อ</h2>");
        emailContent.append("<p>👤 <strong>ชื่อ:</strong> ").append(name).append("</p>");
        emailContent.append("<p>📍 <strong>ที่อยู่:</strong> ").append(address).append("</p>");
        emailContent.append("<p>📞 <strong>เบอร์โทร:</strong> ").append(phone).append("</p>");

        // ✅ เพิ่มตารางแสดงรายการสินค้า + รูปภาพสินค้า
        emailContent.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 100%;'>");
        emailContent.append("<tr><th>รูปสินค้า</th><th>ชื่อสินค้า</th><th>รหัสสินค้า</th><th>ราคา</th><th>จำนวน</th><th>รวม</th></tr>");

        for (Map<String, Object> item : cart) {
            String id = (String) item.getOrDefault("id", "ไม่ระบุ");
            String nameItem = (String) item.getOrDefault("name", "ไม่ระบุ");
            String image = (String) item.getOrDefault("image", "https://online-shopping-frontend-beta.vercel.app/images/default.png");

            // ✅ ถ้า URL ไม่ขึ้นต้นด้วย "http" ให้เติมโฮสต์หลักให้
            if (!image.startsWith("http")) {
                image = "https://online-shopping-frontend-beta.vercel.app" + image;
            }

            double price = 0.0;
            if (item.get("price") instanceof Number) {
                price = ((Number) item.get("price")).doubleValue();
            }

            int quantity = item.get("quantity") instanceof Number ? ((Number) item.get("quantity")).intValue() : 0;
            double totalItemPrice = price * quantity;

            emailContent.append("<tr>")
                    .append("<td><img src='").append(image).append("' width='80' height='80' style='border-radius: 8px;'></td>")
                    .append("<td>").append(nameItem).append("</td>")
                    .append("<td>").append(id).append("</td>")
                    .append("<td>").append(String.format("%.2f", price)).append("฿</td>")
                    .append("<td>").append(quantity).append("</td>")
                    .append("<td>").append(String.format("%.2f", totalItemPrice)).append("฿</td>")
                    .append("</tr>");
        }

        emailContent.append("</table>");
        emailContent.append("<h3>💰 <strong>ราคารวมทั้งหมด:</strong> ").append(String.format("%.2f", totalPrice)).append("฿</h3>");
        emailContent.append("<p>ขอบคุณที่สั่งซื้อกับเรา! 😊</p>");

        return emailContent.toString();
    }
}
