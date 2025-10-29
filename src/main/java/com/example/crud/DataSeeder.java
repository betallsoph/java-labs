package com.example.crud;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(Arrays.asList(
                new Book("Clean Code", "Robert C. Martin"),
                new Book("Effective Java", "Joshua Bloch"),
                new Book("Domain-Driven Design", "Eric Evans")
            ));
        }
    }
}



