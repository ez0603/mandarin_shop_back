package com.example.mandarin_shop_back.dto.inventory.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class InventoryRespDto {
    private int inventoryId;
    private int productId;
    private int inventoryQuantity;
    private int changeQuantity;
    private String inventoryLocation;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
