package io.playdata.springboot04.controller;

import io.playdata.springboot04.model.Product;
import io.playdata.springboot04.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    // Service
    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public List<Product> searchProductsByNameContaining(@RequestParam("name") String name) {
        return productService.findProductsByNameContaining(name);
    }

    @GetMapping("/sorted-by-price")
    public List<Product> findAllByPriceDesc() {
        return productService.findAllByPriceDesc();
    }

    // Create
    @PostMapping // @PostMapping("")
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    // Read All
    @GetMapping
    public List<Product> getProducts() {
        return productService.readAllProducts();
    }

    // Read One
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.readProduct(id);
    }

    // Update
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.updateProduct(id, product);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

}