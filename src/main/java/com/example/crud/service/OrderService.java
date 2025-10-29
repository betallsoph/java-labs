package com.example.crud.service;

import com.example.crud.exception.ResourceAlreadyExistsException;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Order;
import com.example.crud.model.User;
import com.example.crud.repository.OrderRepository;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Profile("!demo")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Order order, String userEmail) {
        // Generate unique order number if not provided
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        // Check if order number already exists
        if (orderRepository.existsByOrderNumber(order.getOrderNumber())) {
            throw new ResourceAlreadyExistsException("Order already exists with number: " + order.getOrderNumber());
        }

        // Set user if provided
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
            order.setUser(user);
        }

        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order order = getOrderById(id);

        // Check if order number is being changed and if it already exists
        if (!order.getOrderNumber().equals(orderDetails.getOrderNumber()) && 
            orderRepository.existsByOrderNumber(orderDetails.getOrderNumber())) {
            throw new ResourceAlreadyExistsException("Order already exists with number: " + orderDetails.getOrderNumber());
        }

        order.setOrderNumber(orderDetails.getOrderNumber());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setProducts(orderDetails.getProducts());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    public List<Order> getOrdersByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        return orderRepository.findByUser(user);
    }
}