package com.example.pantry.service;

import com.example.pantry.model.ShoppingListModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.pantry.repository.ShoppingListRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public void addProduct(ShoppingListModel item) {
        shoppingListRepository.save(item);
    }

    public List<ShoppingListModel> getItemList() {
        return shoppingListRepository.findAll();
    }

    public ShoppingListModel findById(Long shoppingItemId) {
        return shoppingListRepository.findById(shoppingItemId).orElse(null);
    }

    public void deleteItem(Long shoppingItemId) {
        shoppingListRepository.deleteById(shoppingItemId);
    }

}
