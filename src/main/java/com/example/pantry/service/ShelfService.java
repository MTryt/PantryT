package com.example.pantry.service;

import com.example.pantry.model.ShelfModel;
import com.example.pantry.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    public List<ShelfModel> getAllShelves() {
        return shelfRepository.findAll();
    }

    public ShelfModel addShelf(ShelfModel shelfModel) {
        return shelfRepository.save(shelfModel);
    }

    public void deleteShelfById(Long shelfId) {
        shelfRepository.deleteById(shelfId);
    }

    public ShelfModel getShelfById(Long shelfId) {
        return shelfRepository.findById(shelfId).orElse(null);
    }

}