package com.example.pantry.service;

import com.example.pantry.model.ProductModel;
import com.example.pantry.model.ShelfModel;
import com.example.pantry.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductModel product) {
        productRepository.save(product);
    }

    public List<ProductModel> getProductList() {
        return productRepository.findAll();
    }

    public ProductModel findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public void saveEditProduct(ProductModel editProduct) {
        productRepository.save(editProduct);
    }

    public void removeProduct(Long productId) {
        productRepository.deleteById(productId);
    }
    public List<ProductModel> getProductsByShelfAndSortBy(ShelfModel shelf, Sort sort) {
        return productRepository.findByShelf(shelf, sort);
    }



}
