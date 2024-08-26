package com.example.mandarin_shop_back.dto.inventory.request;

import com.example.mandarin_shop_back.entity.inventory.Inventory;
import com.example.mandarin_shop_back.entity.product.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryReqDto {
    private int inventoryId;
    private int productId;
    private int inventoryQuantity;
    private int changeQuantity;
    private String inventoryLocation;

    public Inventory toEntity() {
        return Inventory.builder()
                .inventoryId(inventoryId)
                .productId(productId)
                .inventoryQuantity(inventoryQuantity)
                .changeQuantity(changeQuantity)
                .build();
    }
}
