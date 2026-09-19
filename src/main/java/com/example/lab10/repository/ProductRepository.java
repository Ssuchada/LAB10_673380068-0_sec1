package com.example.lab10.repository;

import com.example.lab10.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {

    private final Map<String, Product> store = new ConcurrentHashMap<>();

    public ProductRepository() {
        store.put("1", new Product(
                "1",
                "สุชาดา ศรีจักรโคตร (673380068-0 SEC 1)",
                "Electronics",
                "Apple",
                50,
                39900.0,
                "MEMBER"
        ));

        store.put("2", new Product(
                "2",
                "MacBook Air M3",
                "Electronics",
                "Apple",
                20,
                49900.0,
                "NONE"
        ));

        store.put("3", new Product(
                "3",
                "Samsung Galaxy S24",
                "Electronics",
                "Samsung",
                30,
                29900.0,
                "SEASONAL"
        ));
    }

    public Mono<Product> findById(String id) {
        Product product = store.get(id);

        if (product == null) {
            return Mono.empty();
        }

        return Mono.just(product);
    }

    public Flux<Product> findAll() {
        return Flux.fromIterable(store.values());
    }

    public Mono<Product> save(Product product) {
        store.put(product.getId(), product);
        return Mono.just(product);
    }

    public Mono<Void> deleteById(String id) {
        store.remove(id);
        return Mono.empty();
    }

    public Flux<Product> findByCategory(String category) {
        return findAll()
                .filter(p -> p.getCategory().equalsIgnoreCase(category));
    }
}