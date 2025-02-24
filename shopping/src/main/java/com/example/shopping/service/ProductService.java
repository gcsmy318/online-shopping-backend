package com.example.shopping.service;

import com.example.shopping.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> products;

    public ProductService() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("products.json").getInputStream();
            products = mapper.readValue(is, new TypeReference<List<Product>>() {});

            // ✅ DEBUG LOG: แสดงสินค้าที่โหลดจาก JSON
            System.out.println("Loaded products: " + products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> getProductsByCategory(String category) {
        // ✅ DEBUG LOG: ตรวจสอบค่าที่รับเข้ามา
        System.out.println("Filtering products by category: " + category);

        List<Product> filteredProducts = products.stream()
                .filter(p -> p.getCode().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        // ✅ DEBUG LOG: แสดงผลลัพธ์หลังจากกรอง
        System.out.println("Filtered products: " + filteredProducts);

        return filteredProducts;
    }
}
