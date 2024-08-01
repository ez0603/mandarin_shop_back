package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    public int saveCart(Cart cart);

    public int deleteCart(int cartId);

    public int updateCart(Cart cart);
}
