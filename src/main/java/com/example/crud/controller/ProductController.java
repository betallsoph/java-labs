package com.example.crud.controller;

import com.example.crud.dto.ApiResponse;
import com.example.crud.model.Product;
import com.example.crud.service.ProductService;
import com.example.crud.service.DemoProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired(required = false)
    private ProductService productService;

    @Autowired(required = false)
    private DemoProductService demoProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products;
        if (demoProductService != null) {
            products = demoProductService.getAllProducts();
        } else {
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) {
        Product product;
        if (demoProductService != null) {
            product = demoProductService.getProductById(id);
        } else {
            product = productService.getProductById(id);
        }
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct;
        if (demoProductService != null) {
            createdProduct = demoProductService.createProduct(product);
        } else {
            createdProduct = productService.createProduct(product);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable String id, 
                                                             @Valid @RequestBody Product product) {
        Product updatedProduct;
        if (demoProductService != null) {
            updatedProduct = demoProductService.updateProduct(id, product);
        } else {
            updatedProduct = productService.updateProduct(id, product);
        }
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> partialUpdateProduct(@PathVariable String id, 
                                                                   @RequestBody Product product) {
        Product updatedProduct;
        if (demoProductService != null) {
            updatedProduct = demoProductService.partialUpdateProduct(id, product);
        } else {
            updatedProduct = productService.partialUpdateProduct(id, product);
        }
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        if (demoProductService != null) {
            demoProductService.deleteProduct(id);
        } else {
            productService.deleteProduct(id);
        }
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }
}