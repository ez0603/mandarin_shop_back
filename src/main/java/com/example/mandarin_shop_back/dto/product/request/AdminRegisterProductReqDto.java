package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.dto.product.response.OptionsRespDto;
import com.example.mandarin_shop_back.entity.inventory.Inventory;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.product.OptionTitle;
import com.example.mandarin_shop_back.entity.product.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminRegisterProductReqDto {
    private String productName;
    private int categoryId;
    private int productPrice;
    private String productImg;
    private String productDescription;

    private int inventoryQuantity;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .categoryId(categoryId)
                .productPrice(productPrice)
                .productImg(productImg)
                .productDescription(productDescription)
                .build();
    }

    public Inventory toInventoryEntity(int productId) {
        return Inventory.builder()
                .productId(productId)
                .inventoryQuantity(inventoryQuantity)
                .build();
    }

}
