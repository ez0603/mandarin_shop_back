package com.example.mandarin_shop_back.service.cart;

import com.example.mandarin_shop_back.dto.cart.request.CartItemReqDto;
import com.example.mandarin_shop_back.dto.cart.request.CartReqDto;
import com.example.mandarin_shop_back.dto.cart.response.CartItemRespDto;
import com.example.mandarin_shop_back.dto.cart.response.CartRespDto;
import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.cart.CartItem;
import com.example.mandarin_shop_back.repository.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    @Transactional(rollbackFor = Exception.class)
    public int saveCart(CartReqDto cartReqDto) {
        return cartMapper.saveCart(cartReqDto.toEntity());
    }

    public List<CartRespDto> getCart(int userId) {
        List<Cart> carts = cartMapper.getCart(userId);

        return carts.stream().map(Cart::toSearchCartRespDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteCart(int cartId) {
        return cartMapper.deleteCart(cartId);
    }

    @Transactional
    public void editCart(CartReqDto cartReqDto) {
        Cart cart = cartReqDto.toEntity();
        cartMapper.updateCart(cart);
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveCartItem(CartItemReqDto cartItemReqDto) {
        return cartMapper.saveCartItem(cartItemReqDto.toEntity());
    }

    public List<CartItemRespDto> getCartItem(int cartId) {
        List<CartItem> cartItems = cartMapper.getCartItem(cartId);

        return cartItems.stream().map(CartItem::toSearchCartItemRespDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteCartItem(int cartItemId) {
        return cartMapper.deleteCartItem(cartItemId);
    }

    @Transactional
    public void editCartItem(CartItemReqDto cartItemReqDto) {
        CartItem cartItem = cartItemReqDto.toEntity();
        cartMapper.updateCartItem(cartItem);
    }

}
