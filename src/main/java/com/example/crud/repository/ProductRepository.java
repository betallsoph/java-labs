package com.example.crud.repository;

import com.example.crud.model.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("!demo")
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);
}