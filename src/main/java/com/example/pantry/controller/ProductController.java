package com.example.pantry.controller;

import com.example.pantry.model.ProductStatus;
import com.example.pantry.model.ProductModel;
import com.example.pantry.model.ShelfModel;
import com.example.pantry.service.ProductService;
import com.example.pantry.service.ShelfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.util.List;
@Controller

public class ProductController {

    private final ProductService productService;
    //nowe
    private final ShelfService shelfService;

    public ProductController(ProductService productService, ShelfService shelfService) {

        this.productService = productService;
        this.shelfService = shelfService;
    }
//stare i działa
    @GetMapping("/shelfView")
    public String getProductList(Model model){
        List<ProductModel> productModelList = productService.getProductList();
        model.addAttribute("productList", productModelList);
        return "shelves/shelfView";
    }

    //nowe
    @GetMapping("/addProduct")
    public String showAddProductView(@RequestParam("shelfId") Long shelfId, Model model) {
        List<ShelfModel> shelves = shelfService.getAllShelves();
        model.addAttribute("shelves", shelves);
        model.addAttribute("productModel", new ProductModel());
        model.addAttribute("shelfId", shelfId); // TO TU Przekazuje wartość shelfId do widoku
        model.addAttribute("statusOfProduct", ProductStatus.values());
        return "products/addProductView";
    }

    //stare post maping do addProduct
//    @PostMapping("/addProduct")
//    public RedirectView postAddProductAction(ProductModel productModel, @RequestParam Long shelfId) {
//        productService.addProduct(productModel);
//        return new RedirectView("/view/" + shelfId);
//    }
//nowe z walidacją
    @PostMapping("/addProduct")
    public String postAddProductAction(@ModelAttribute @Valid ProductModel productModel, BindingResult result, @RequestParam Long shelfId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productModel", productModel);
            return "products/addProductView";
        }

        productService.addProduct(productModel);
        return "redirect:/view/" + shelfId;
    }

    @PostMapping("/deleteProduct/{productId}")
    public RedirectView deleteProductAction(@PathVariable Long productId, @RequestParam Long shelfId, Model model){
        productService.removeProduct(productId);
        return new RedirectView("/view/" + shelfId);
    }

    @GetMapping("/edit/{productId}")
    public String showEditForm(@PathVariable("productId") Long productId, Model model, @RequestParam Long shelfId) {
        ProductModel existingProduct = productService.findById(productId);
        model.addAttribute("existingProduct", existingProduct);
        ShelfModel existingShelf = shelfService.getShelfById(shelfId);
        model.addAttribute("shelf", existingShelf);
        model.addAttribute("statusOfProduct", ProductStatus.values());
        return "products/editProductView";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute @Valid ProductModel editProduct, BindingResult result, @RequestParam Long shelfId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("existingProduct", editProduct);
            model.addAttribute("statusOfProduct", ProductStatus.values());
            return "products/editProductView";
        }

        ShelfModel existingShelf = shelfService.getShelfById(shelfId);
        editProduct.setShelf(existingShelf);
        productService.saveEditProduct(editProduct);

        return "redirect:/view/" + shelfId;
    }

//stare
//    @PostMapping("/products/save")
//    public RedirectView saveProduct(ProductModel editProduct, @RequestParam Long shelfId){
//        ShelfModel existingShelf = shelfService.getShelfById(shelfId);
//       editProduct.setShelf(existingShelf);
//        productService.saveEditProduct(editProduct);
//
//       return new RedirectView("/view/" + shelfId);
//   }


}
