package com.fatih.productservice.service;

import com.fatih.productservice.dto.ProductRequest;
import com.fatih.productservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    boolean existById(Long id);

    void decreaseStock(Long productId, Integer quantity);

}
