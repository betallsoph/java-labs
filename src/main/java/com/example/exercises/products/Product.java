package com.example.exercises.products;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Product document.
 */
@Data
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private Double price;

    private String imageUrl;

    private String description;
}
