package com.example.lab10;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;

@SpringBootTest
class Lab10ApplicationTests {

    @Autowired
    private ProductRepository repository;

    @Test
    void contextLoads() {
        // Spring Application Context โหลดสำเร็จ
    }

    @Test
    void testFindById_found() {
        StepVerifier.create(repository.findById("1"))
                .expectNextMatches(p -> p.getName().contains("iPhone"))
                .verifyComplete();
    }

    @Test
    void testFindById_notFound() {
        StepVerifier.create(repository.findById("999"))
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DirtiesContext
    void testSave() {
        Product product = new Product(
                "4",
                "iPad Pro",
                "Electronics",
                "Apple",
                10,
                39900.0,
                "MEMBER"
        );

        StepVerifier.create(repository.save(product))
                .expectNextMatches(p ->
                        p.getId().equals("4")
                        && p.getName().equals("iPad Pro")
                        && p.getCategory().equals("Electronics")
                )
                .verifyComplete();
    }

    @Test
    void testFindByCategory() {
        StepVerifier.create(repository.findByCategory("Electronics"))
                .expectNextCount(3)
                .verifyComplete();
    }
}