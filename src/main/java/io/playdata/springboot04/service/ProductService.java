package io.playdata.springboot04.service;

import io.playdata.springboot04.model.Product;
import io.playdata.springboot04.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 의존성 주입이 될 클래스를 받아줄 생성자 생성
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public List<Product> findProductsByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findAllByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    // Create
    public void createProduct(Product product) {
        productRepository.save(product); // Repository에는 save! -> create
    }

    // Read
    public Product readProduct(Long id) {
        return productRepository.findById(id).orElse(null); // findById -> read
    }

    // Read All
    public List<Product> readAllProducts() {
        return productRepository.findAll(); // findAll -> readAll
    }

    // Update
    // https://sharegpt.com/c/q8ul7jK
    public void updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
    }


    // Delete
    public void deleteProduct(Long id) {
        productRepository.deleteById(id); // deleteById -> delete
    }
}