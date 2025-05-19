package com.fatih.productservice.service.impl;

import com.fatih.productservice.dto.ProductRequest;
import com.fatih.productservice.entity.Product;
import com.fatih.productservice.exception.InsufficientStockException;
import com.fatih.productservice.exception.NotFoundException;
import com.fatih.productservice.repository.ProductRepository;
import com.fatih.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final  ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id " + id + "not found."));
    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = getProduct(productRequest);

        return productRepository.save(product);
    }

    private static Product getProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean existById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Ürün bulunamadı"));

        if (product.getQuantity() < quantity) {
            log.warn("Stok yetersiz! productId: {}", productId);
            throw new InsufficientStockException("Stok yetersiz! productId: " + productId); // İleride: retry, compensation, notification yapılabilir
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

}
