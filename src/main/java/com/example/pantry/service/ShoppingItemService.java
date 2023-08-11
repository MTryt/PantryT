package com.example.pantry.service;

import com.example.pantry.model.ShoppingItemModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pantry.repository.ShoppingItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;

    public void addProduct(ShoppingItemModel item) {
        shoppingItemRepository.save(item);
    }

    public List<ShoppingItemModel> getItemList() {
        return shoppingItemRepository.findAll();
    }

    public ShoppingItemModel findById(Long shoppingItemId) {
        return shoppingItemRepository.findById(shoppingItemId).orElse(null);
    }

    public void deleteItem(Long shoppingItemId) {
        shoppingItemRepository.deleteById(shoppingItemId);
    }

    public Page<ShoppingItemModel> getAllItems(Pageable pageable) {
        return shoppingItemRepository.findAll(pageable);
    }


}
