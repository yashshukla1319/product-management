package com.coachbar.product.management.service;

import com.coachbar.product.management.exceptions.InvalidResourceException;
import com.coachbar.product.management.exceptions.ResourceNotFoundException;
import com.coachbar.product.management.model.entity.Product;
import com.coachbar.product.management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger LOGGER = Logger.getLogger("ProductService");

    //Autowired
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        LOGGER.info("Inside getProducts:");
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id)
//                .map(product ->product)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id does not exists.")));
    }

    public Product createProduct(Product product) {
        productRepository.findByName(product.getName()).ifPresent(existingProduct -> {
            throw new InvalidResourceException("Product already exists.");
        });

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setQuantity(productDetails.getQuantity());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(product);
    }
}
