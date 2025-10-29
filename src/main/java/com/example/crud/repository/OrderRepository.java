package com.example.crud.repository;

import com.example.crud.model.Order;
import com.example.crud.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("!demo")
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUser(User user);
    boolean existsByOrderNumber(String orderNumber);
}