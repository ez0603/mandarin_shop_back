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
    public ResponseEntity<?> registerProduct(@RequestBody AdminRegisterProductReqDto adminRegisterProductReqDto) {
        adminProductService.saveProduct(adminRegisterProductReqDto);
        return ResponseEntity.ok("Product and inventory saved successfully");
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductReqDto updateProductReqDto) {
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

    @GetMapping("/products/category")
    public ResponseEntity<?> getProductCategory(@RequestParam int categoryId) {
        return ResponseEntity.ok(adminProductService.getProductCategory(categoryId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestParam int productId) {
        return ResponseEntity.ok(adminProductService.getProductDetail(productId));
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

    @PostMapping("/option/name")
    public ResponseEntity<?> addOptionName(@RequestBody AddOptionNameReqDto addOptionNameReqDto) {
        adminProductService.insertOptionName(addOptionNameReqDto);
        return ResponseEntity.created(null).body(true);
    }

    @GetMapping("/option")
    public ResponseEntity<?> getOptionsByAdminId(@RequestParam int productId) {
        return ResponseEntity.ok(adminProductService.getOptionsByMenuId(productId));
    }

    @PutMapping("/option")
    public ResponseEntity<?> updateOptionName(@RequestBody UpdateOptionNameReqDto updateOptionNameReqDto) {
        adminProductService.editOptionName(updateOptionNameReqDto);
        return ResponseEntity.ok(true);
    }



}
