package com.example.exercises.products;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST: /api/products and /api/products/{id}
 * - GET list/details: public
 * - POST/PUT/PATCH/DELETE: require login (configured via SecurityConfig)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    @GetMapping
    public List<Product> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        product.setId(null);
        if (repository.existsByCode(product.getCode())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Duplicate product code"));
        }
        return ResponseEntity.ok(repository.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        return repository.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@PathVariable String id, @Valid @RequestBody Product updated) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        updated.setId(id);
        return ResponseEntity.ok(repository.save(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> patch) {
        return repository.findById(id).map(existing -> {
            if (patch.containsKey("code")) existing.setCode((String) patch.get("code"));
            if (patch.containsKey("name")) existing.setName((String) patch.get("name"));
            if (patch.containsKey("price")) existing.setPrice(Double.valueOf(patch.get("price").toString()));
            if (patch.containsKey("imageUrl")) existing.setImageUrl((String) patch.get("imageUrl"));
            if (patch.containsKey("description")) existing.setDescription((String) patch.get("description"));
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
