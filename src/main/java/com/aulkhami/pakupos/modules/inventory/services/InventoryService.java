package com.aulkhami.pakupos.modules.inventory.services;

import com.aulkhami.pakupos.modules.inventory.dtos.ProductRequestDTO;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.inventory.entities.Product;
import com.aulkhami.pakupos.modules.inventory.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService() {
        this.productRepository = new ProductRepository();
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) throws IllegalArgumentException {
        // Validation
        if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (requestDTO.getPrice() == null || requestDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be a valid positive number");
        }
        if (requestDTO.getStock() == null || requestDTO.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        // Map DTO to Entity
        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setCategory(requestDTO.getCategory());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDTO(savedProduct);
    }
}
