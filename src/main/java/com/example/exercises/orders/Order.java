package com.example.exercises.orders;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Order document referencing product IDs for simplicity.
 */
@Data
@NoArgsConstructor
@Document("orders")
public class Order {
    @Id
    private String id;

    @NotBlank
    private String orderNumber;

    @NotNull
    @PositiveOrZero
    private Double totalPrice;

    private List<String> productIds; // list of Product.id
}
