package com.example.mandarin_shop_back.controller.cart;

import com.example.mandarin_shop_back.dto.cart.request.CartReqDto;
import com.example.mandarin_shop_back.dto.product.request.AdminRegisterProductReqDto;
import com.example.mandarin_shop_back.dto.product.request.UpdateProductReqDto;
import com.example.mandarin_shop_back.service.admin.AdminProductService;
import com.example.mandarin_shop_back.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/carts")
    public ResponseEntity<?> registerMenu(@RequestBody CartReqDto CartReqDto) {
        return ResponseEntity.ok().body(cartService.saveCart((CartReqDto)));
    }

    @DeleteMapping("/carts")
    public ResponseEntity<?> deleteProduct(@RequestParam int cartId) {
        return ResponseEntity.ok(cartService.deleteCart(cartId));
    }

    @PutMapping("/carts")
    public ResponseEntity<?> updateCart(@RequestBody CartReqDto CartReqDto) {
        cartService.editProduct(CartReqDto);
        return ResponseEntity.ok(true);
    }
}
