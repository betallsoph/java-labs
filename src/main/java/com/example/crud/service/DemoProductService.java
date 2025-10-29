package com.example.crud.service;

import com.example.crud.exception.ResourceAlreadyExistsException;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("demo")
public class DemoProductService {

    @Autowired
    private Map<String, Product> productStorage;

    @Autowired
    private AtomicLong idCounter;

    public List<Product> getAllProducts() {
        return productStorage.values().stream().toList();
    }

    public Product getProductById(String id) {
        return productStorage.values().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        // Check if code already exists
        boolean codeExists = productStorage.values().stream()
                .anyMatch(p -> p.getCode().equals(product.getCode()));
        
        if (codeExists) {
            throw new ResourceAlreadyExistsException("Product already exists with code: " + product.getCode());
        }
        
        product.setId(String.valueOf(idCounter.getAndIncrement()));
        product.setCreatedAt(LocalDateTime.now());
        productStorage.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(String id, Product productDetails) {
        Product product = getProductById(id);
        
        // Check if code is being changed and if it already exists
        if (!product.getCode().equals(productDetails.getCode())) {
            boolean codeExists = productStorage.values().stream()
                    .anyMatch(p -> p.getCode().equals(productDetails.getCode()));
            if (codeExists) {
                throw new ResourceAlreadyExistsException("Product already exists with code: " + productDetails.getCode());
            }
        }

        product.setCode(productDetails.getCode());
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setIllustration(productDetails.getIllustration());
        product.setDescription(productDetails.getDescription());
        product.setUpdatedAt(LocalDateTime.now());

        productStorage.put(product.getId(), product);
        return product;
    }

    public Product partialUpdateProduct(String id, Product productDetails) {
        Product product = getProductById(id);

        if (productDetails.getCode() != null) {
            if (!product.getCode().equals(productDetails.getCode())) {
                boolean codeExists = productStorage.values().stream()
                        .anyMatch(p -> p.getCode().equals(productDetails.getCode()));
                if (codeExists) {
                    throw new ResourceAlreadyExistsException("Product already exists with code: " + productDetails.getCode());
                }
            }
            product.setCode(productDetails.getCode());
        }
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getIllustration() != null) {
            product.setIllustration(productDetails.getIllustration());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        
        product.setUpdatedAt(LocalDateTime.now());
        productStorage.put(product.getId(), product);
        return product;
    }

    public void deleteProduct(String id) {
        Product product = getProductById(id);
        productStorage.remove(product.getId());
    }
}