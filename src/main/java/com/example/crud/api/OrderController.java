package com.example.crud.api;

import com.example.crud.order.Order;
import com.example.crud.order.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public List<Order> list() {
        return orderRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Order order) {
        if (orderRepository.existsByOrderNumber(order.getOrderNumber())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Duplicate order number"));
        }
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return orderRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Order not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@PathVariable String id, @RequestBody @Valid Order order) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Order not found"));
        }
        order.setId(id);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Order not found"));
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
