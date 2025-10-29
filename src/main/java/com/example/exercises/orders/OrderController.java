package com.example.exercises.orders;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST: /api/orders and /api/orders/{id}
 * - GET list/details: public
 * - POST/PUT/DELETE: require login (configured via SecurityConfig)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository repository;

    @GetMapping
    public List<Order> list() { return repository.findAll(); }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Order order) {
        order.setId(null);
        return ResponseEntity.ok(repository.save(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        return repository.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@PathVariable String id, @Valid @RequestBody Order updated) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        updated.setId(id);
        return ResponseEntity.ok(repository.save(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
