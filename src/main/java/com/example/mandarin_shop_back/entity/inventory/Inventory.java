package com.example.mandarin_shop_back.entity.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private int inventoryId;
    private int productId;
    private int inventoryQuantity;
    private int changeQuantity;
    private String inventoryLocation;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
