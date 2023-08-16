package com.example.pantry.controller;

import com.example.pantry.enums.MeasurementUnitEnum;
import com.example.pantry.model.ProductModel;
import com.example.pantry.model.ShelfModel;
import com.example.pantry.model.ShoppingListModel;
import com.example.pantry.service.ProductService;
import com.example.pantry.service.ShelfService;
import com.example.pantry.service.ShoppingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ProductService productService;
    private final ShelfService shelfService;

    public ShoppingListController(ShoppingListService shoppingListService, ProductService productService, ShelfService shelfService) {
        this.shoppingListService = shoppingListService;
        this.productService = productService;
        this.shelfService = shelfService;
    }

    @GetMapping("/shoppingList")
    public String getItemList(Model model) {
        List<ShelfModel> shelfModelList = shelfService.getAllShelves();
        model.addAttribute("itemList", shoppingListService.getItemList());
        model.addAttribute("shelves", shelfModelList);
        return "shoppingList/shoppingListView";
    }

    @GetMapping("/addProductSL")
    public String showAddItemView(Model model){
        List<ProductModel> productModelList = productService.getProductList();
        List<ShoppingListModel> shoppingItemList = shoppingListService.getItemList();
        model.addAttribute("shoppingItem", new ShoppingListModel());
        model.addAttribute("itemList", shoppingItemList);
        model.addAttribute("products", productModelList);
        model.addAttribute("measurementUnit", MeasurementUnitEnum.values());
        return "shoppingList/addProductToShoppingListView";
    }

    @PostMapping("/addProductSL")
    public String postAddItemAction(@Valid @ModelAttribute("shoppingItem") ShoppingListModel shoppingListModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ProductModel> productModelList = productService.getProductList();
            List<ShoppingListModel> shoppingItemList = shoppingListService.getItemList();

            model.addAttribute("itemList", shoppingItemList);
            model.addAttribute("products", productModelList);
            model.addAttribute("measurementUnit", MeasurementUnitEnum.values());
            return "shoppingList/addProductToShoppingListView";
        }

        shoppingListService.addProduct(shoppingListModel);
        return "redirect:/shoppingList";
    }

    @PostMapping("/deleteProductSL/{shoppingItemId}")
    public RedirectView deleteItemAction(@PathVariable Long shoppingItemId){
        shoppingListService.deleteItem(shoppingItemId);
        return new RedirectView("/shoppingList");
    }

    @PostMapping("/sendProduct/{shoppingItemId}")
    public RedirectView sendItemAction(@PathVariable Long shoppingItemId) {
        ShoppingListModel existingItem = shoppingListService.findById(shoppingItemId);
        List<ProductModel> allProduct = productService.getProductList();
        for (ProductModel product : allProduct) {
            if (product.getProductName().equals(existingItem.getItemName()) &&
                    product.getMeasurementUnit() == existingItem.getMeasurementUnit()) {
                product.setQuantityOfProduct(product.getQuantityOfProduct() + existingItem.getItemQuantity());
                productService.saveEditProduct(product);
                shoppingListService.deleteItem(existingItem.getShoppingItemId());
                }
            }
            return new RedirectView("/shoppingList");
        }

    }

