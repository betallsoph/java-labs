package com.example.crud.service;

import com.example.crud.exception.ResourceAlreadyExistsException;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Order;
import com.example.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("demo")
public class DemoOrderService {

    @Autowired
    private Map<String, Order> orderStorage;

    @Autowired
    private Map<String, User> userStorage;

    @Autowired
    private AtomicLong idCounter;

    public List<Order> getAllOrders() {
        return orderStorage.values().stream().toList();
    }

    public Order getOrderById(String id) {
        return orderStorage.values().stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Order order, String userEmail) {
        // Generate unique order number if not provided
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        // Check if order number already exists
        boolean orderNumberExists = orderStorage.values().stream()
                .anyMatch(o -> o.getOrderNumber().equals(order.getOrderNumber()));
        
        if (orderNumberExists) {
            throw new ResourceAlreadyExistsException("Order already exists with number: " + order.getOrderNumber());
        }

        // Set user if provided
        if (userEmail != null) {
            User user = userStorage.values().stream()
                    .filter(u -> u.getEmail().equals(userEmail))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
            order.setUser(user);
        }

        order.setId(String.valueOf(idCounter.getAndIncrement()));
        order.setCreatedAt(LocalDateTime.now());
        orderStorage.put(order.getId(), order);
        return order;
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order order = getOrderById(id);

        // Check if order number is being changed and if it already exists
        if (!order.getOrderNumber().equals(orderDetails.getOrderNumber())) {
            boolean orderNumberExists = orderStorage.values().stream()
                    .anyMatch(o -> o.getOrderNumber().equals(orderDetails.getOrderNumber()));
            if (orderNumberExists) {
                throw new ResourceAlreadyExistsException("Order already exists with number: " + orderDetails.getOrderNumber());
            }
        }

        order.setOrderNumber(orderDetails.getOrderNumber());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setProducts(orderDetails.getProducts());
        order.setUpdatedAt(LocalDateTime.now());

        orderStorage.put(order.getId(), order);
        return order;
    }

    public void deleteOrder(String id) {
        Order order = getOrderById(id);
        orderStorage.remove(order.getId());
    }

    public List<Order> getOrdersByUser(String userEmail) {
        User user = userStorage.values().stream()
                .filter(u -> u.getEmail().equals(userEmail))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        
        return orderStorage.values().stream()
                .filter(order -> order.getUser() != null && order.getUser().getId().equals(user.getId()))
                .toList();
    }
}