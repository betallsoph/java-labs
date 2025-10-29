package com.example.crud.controller;

import com.example.crud.dto.ApiResponse;
import com.example.crud.model.Order;
import com.example.crud.security.CustomUserDetails;
import com.example.crud.service.OrderService;
import com.example.crud.service.DemoOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired(required = false)
    private OrderService orderService;

    @Autowired(required = false)
    private DemoOrderService demoOrderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders;
        if (demoOrderService != null) {
            orders = demoOrderService.getAllOrders();
        } else {
            orders = orderService.getAllOrders();
        }
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String id) {
        Order order;
        if (demoOrderService != null) {
            order = demoOrderService.getOrderById(id);
        } else {
            order = orderService.getOrderById(id);
        }
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody Order order) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            userEmail = userDetails.getUser().getEmail();
        }

        Order createdOrder;
        if (demoOrderService != null) {
            createdOrder = demoOrderService.createOrder(order, userEmail);
        } else {
            createdOrder = orderService.createOrder(order, userEmail);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order created successfully", createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable String id, 
                                                         @Valid @RequestBody Order order) {
        Order updatedOrder;
        if (demoOrderService != null) {
            updatedOrder = demoOrderService.updateOrder(id, order);
        } else {
            updatedOrder = orderService.updateOrder(id, order);
        }
        return ResponseEntity.ok(ApiResponse.success("Order updated successfully", updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable String id) {
        if (demoOrderService != null) {
            demoOrderService.deleteOrder(id);
        } else {
            orderService.deleteOrder(id);
        }
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully", null));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String userEmail = userDetails.getUser().getEmail();
            List<Order> orders;
            if (demoOrderService != null) {
                orders = demoOrderService.getOrdersByUser(userEmail);
            } else {
                orders = orderService.getOrdersByUser(userEmail);
            }
            return ResponseEntity.ok(ApiResponse.success(orders));
        }
        return ResponseEntity.ok(ApiResponse.success(List.of()));
    }
}