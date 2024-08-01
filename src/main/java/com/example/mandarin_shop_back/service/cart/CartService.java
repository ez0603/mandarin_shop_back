package com.example.mandarin_shop_back.service.cart;

import com.example.mandarin_shop_back.dto.cart.request.CartReqDto;
import com.example.mandarin_shop_back.dto.product.request.AdminRegisterProductReqDto;
import com.example.mandarin_shop_back.dto.product.request.UpdateProductReqDto;
import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.product.Product;
import com.example.mandarin_shop_back.repository.CartMapper;
import com.example.mandarin_shop_back.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    @Transactional(rollbackFor = Exception.class)
    public int saveCart(CartReqDto cartReqDto) {
        return cartMapper.saveCart(cartReqDto.toEntity());
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteCart(int cartId) {
        return cartMapper.deleteCart(cartId);
    }

    @Transactional
    public void editProduct(CartReqDto cartReqDto) {
        Cart cart = cartReqDto.toEntity();
        cartMapper.updateCart(cart);
    }
}
