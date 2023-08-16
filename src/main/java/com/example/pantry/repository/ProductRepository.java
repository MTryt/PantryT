package com.example.pantry.repository;

import com.example.pantry.model.ProductModel;
import com.example.pantry.model.ShelfModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Page<ProductModel>findAll(Pageable pageable);

    List<ProductModel> findByShelf(ShelfModel shelf, Sort sort);


}
