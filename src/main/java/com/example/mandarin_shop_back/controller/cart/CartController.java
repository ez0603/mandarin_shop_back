package com.example.mandarin_shop_back.controller.cart;

import com.example.mandarin_shop_back.dto.cart.request.CartItemReqDto;
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
    public ResponseEntity<?> registerCart(@RequestBody CartReqDto CartReqDto) {
        return ResponseEntity.ok().body(cartService.saveCart((CartReqDto)));
    }

    @GetMapping("/carts")
    public ResponseEntity<?> getCart(@RequestParam int userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/carts")
    public ResponseEntity<?> deleteCart(@RequestParam int cartId) {
        return ResponseEntity.ok(cartService.deleteCart(cartId));
    }

    @PutMapping("/carts")
    public ResponseEntity<?> updateCart(@RequestBody CartReqDto CartReqDto) {
        cartService.editCart(CartReqDto);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/item")
    public ResponseEntity<?> registerCartItem(@RequestBody CartItemReqDto cartItemReqDto) {
        return ResponseEntity.ok().body(cartService.saveCartItem((cartItemReqDto)));
    }

    @GetMapping("/item")
    public ResponseEntity<?> getCartItem(@RequestParam int cartId) {
        return ResponseEntity.ok(cartService.getCartItem(cartId));
    }

    @DeleteMapping("/item")
    public ResponseEntity<?> deleteCartItem(@RequestParam int cartItemId) {
        return ResponseEntity.ok(cartService.deleteCartItem(cartItemId));
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateCartItem(@RequestBody CartItemReqDto cartItemReqDto) {
        cartService.editCartItem(cartItemReqDto);
        return ResponseEntity.ok(true);
    }

}
