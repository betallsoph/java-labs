package com.example.crud.api;

import com.example.crud.product.Product;
import com.example.crud.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> list() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Product product) {
        if (productRepository.existsByCode(product.getCode())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Duplicate product code"));
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return productRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Product not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@PathVariable String id, @RequestBody @Valid Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Product not found"));
        }
        product.setId(id);
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartial(@PathVariable String id, @RequestBody Map<String, Object> patch) {
        var opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Product not found"));
        }
        var p = opt.get();
        if (patch.containsKey("code")) p.setCode(String.valueOf(patch.get("code")));
        if (patch.containsKey("name")) p.setName(String.valueOf(patch.get("name")));
        if (patch.containsKey("price")) p.setPrice(Double.parseDouble(String.valueOf(patch.get("price"))));
        if (patch.containsKey("imageUrl")) p.setImageUrl(String.valueOf(patch.get("imageUrl")));
        if (patch.containsKey("description")) p.setDescription(String.valueOf(patch.get("description")));
        return ResponseEntity.ok(productRepository.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Product not found"));
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
