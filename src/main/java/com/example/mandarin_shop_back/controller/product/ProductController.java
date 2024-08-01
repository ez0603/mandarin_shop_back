package com.example.mandarin_shop_back.controller.product;

import com.example.mandarin_shop_back.dto.product.request.*;
import com.example.mandarin_shop_back.service.admin.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private AdminProductService adminProductService;

    @PostMapping("/products")
    public ResponseEntity<?> registerMenu(@RequestBody AdminRegisterProductReqDto adminRegisterProductReqDto) {
        return ResponseEntity.ok().body(adminProductService.saveProduct((adminRegisterProductReqDto)));
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateMenu(@RequestBody UpdateProductReqDto updateProductReqDto) {
        adminProductService.editProduct(updateProductReqDto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestParam int productId) {
        return ResponseEntity.ok(adminProductService.deleteProduct(productId));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok(adminProductService.getProducts());
    }

    @PostMapping("/category")
    public ResponseEntity<?> addMenuCategory(@RequestBody AddProductCategoryReqDto addProductCategoryReqDto) {
        adminProductService.insertProductCategory(addProductCategoryReqDto);
        return ResponseEntity.created(null).body(true);
    }

    @PutMapping("/category")
    public ResponseEntity<?> updateProductCategory(@RequestBody UpdateProductCategoryReqDto updateProductCategoryReqDto) {
        adminProductService.editProductCategory(updateProductCategoryReqDto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteProductCategory(@RequestParam int categoryId) {
        return ResponseEntity.ok(adminProductService.deleteProductCategory(categoryId));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok().body(adminProductService.getCategory());
    }

    @PostMapping("/option/title")
    public ResponseEntity<?> addOptionTitle(@RequestBody AddOptionTitleReqDto addOptionTitleReqDto) {
        adminProductService.insertOptionTitle(addOptionTitleReqDto);
        return ResponseEntity.created(null).body(true);
    }

    @GetMapping("/option/title")
    public ResponseEntity<?> getOptionTitles(@RequestParam int productId) {
        adminProductService.getOptionTitles(productId);
        return ResponseEntity.ok(adminProductService.getOptionTitles(productId));
    }

    @PutMapping("/option/title")
    public ResponseEntity<?> updateOptionTitle(@RequestBody UpdateOptionTitleReqDto updateOptionTitleReqDto) {
        adminProductService.editOptionTitle(updateOptionTitleReqDto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/option/title")
    public ResponseEntity<?> deleteOptionTitle(@RequestBody DeleteOptionTitleReqDto deleteOptionTitleReqDto) {
        adminProductService.deleteOptionTitle(deleteOptionTitleReqDto);
        return ResponseEntity.ok(true);
    }

}
