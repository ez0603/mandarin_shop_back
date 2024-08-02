package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.cart.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    public int saveCart(Cart cart);

    public List<Cart> getCart(@Param("userId") int userId);

    public int deleteCart(int cartId);

    public int updateCart(Cart cart);

    public int saveCartItem(CartItem cartItem);

    public List<CartItem> getCartItem(@Param("cartId") int cartId);

    public int deleteCartItem(int cartItemId);

    public int updateCartItem(CartItem cartItem);
}
