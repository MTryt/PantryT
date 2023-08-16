package com.example.pantry.controller;

import com.example.pantry.model.ProductModel;
import com.example.pantry.model.ShelfModel;
import com.example.pantry.service.ProductService;
import com.example.pantry.service.ShelfService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShelfController {
    private final ShelfService shelfService;
    private final ProductService productService;

    public ShelfController(ShelfService shelfService, ProductService productService) {
        this.shelfService = shelfService;
        this.productService = productService;
    }

    @GetMapping("/shelves")
    public String showShelves(Model model) {
        List<ShelfModel> shelves = shelfService.getAllShelves();
        model.addAttribute("shelves", shelves);
        model.addAttribute("newShelf", new ShelfModel());
        return "shelves/shelvesView";
    }

    @PostMapping("/shelves/add")
    public String addShelf(@Valid @ModelAttribute ShelfModel shelfModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("shelves", shelfService.getAllShelves());
            model.addAttribute("newShelf", shelfModel);
            return "shelves/shelvesView";
        }
        shelfService.addShelf(shelfModel);
        return "redirect:/shelves";
    }

    @PostMapping("/shelves/delete/{shelfId}")
    public String deleteShelf(@PathVariable Long shelfId) {
        shelfService.deleteShelfById(shelfId);
        return "redirect:/shelves";
    }

    @GetMapping("shelf/{shelfId}")
    public String viewShelf(@PathVariable("shelfId") Long shelfId,
                            @RequestParam(name = "sortField", required = false, defaultValue = "productName") String sortField,
                            @RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
                            Model model) {
        ShelfModel shelf = shelfService.getShelfById(shelfId);
        Sort.Direction direction = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<ProductModel> products = productService.getProductsByShelfAndSortBy(shelf, Sort.by(direction, sortField));

        model.addAttribute("shelf", shelf);
        model.addAttribute("products", products);

        return "shelves/shelfView";
    }

}
