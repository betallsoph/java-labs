package com.example.crud.config;

import com.example.crud.model.Product;
import com.example.crud.model.User;
import com.example.crud.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@Profile("demo")
public class DemoConfig {

    @Bean
    public Map<String, User> userStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<String, Product> productStorage() {
        Map<String, Product> storage = new ConcurrentHashMap<>();
        
        // Initialize with sample data
        AtomicLong idCounter = new AtomicLong(1);
        
        List<Product> sampleProducts = List.of(
            Product.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .code("LAPTOP001")
                .name("Gaming Laptop")
                .price(new BigDecimal("1299.99"))
                .illustration("https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400")
                .description("High-performance gaming laptop with RTX graphics")
                .createdAt(LocalDateTime.now())
                .build(),
            
            Product.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .code("PHONE001")
                .name("Smartphone Pro")
                .price(new BigDecimal("899.99"))
                .illustration("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400")
                .description("Latest smartphone with advanced camera system")
                .createdAt(LocalDateTime.now())
                .build(),
            
            Product.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .code("HEADSET001")
                .name("Wireless Headphones")
                .price(new BigDecimal("199.99"))
                .illustration("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400")
                .description("Premium noise-canceling wireless headphones")
                .createdAt(LocalDateTime.now())
                .build(),
            
            Product.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .code("WATCH001")
                .name("Smart Watch")
                .price(new BigDecimal("299.99"))
                .illustration("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400")
                .description("Advanced fitness tracking and smart features")
                .createdAt(LocalDateTime.now())
                .build(),
            
            Product.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .code("TABLET001")
                .name("Pro Tablet")
                .price(new BigDecimal("599.99"))
                .illustration("https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400")
                .description("Professional tablet for creative work")
                .createdAt(LocalDateTime.now())
                .build()
        );
        
        sampleProducts.forEach(product -> storage.put(product.getId(), product));
        
        return storage;
    }

    @Bean
    public Map<String, Order> orderStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public AtomicLong idCounter() {
        return new AtomicLong(6); // Start after sample products
    }
}