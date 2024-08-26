package com.example.mandarin_shop_back.entity.inventory;

import com.example.mandarin_shop_back.dto.inventory.response.InventoryRespDto;
import com.example.mandarin_shop_back.dto.product.response.AdminSearchProductRespDto;
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
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public InventoryRespDto toSearchInventoryRespDto() {
        return InventoryRespDto.builder()
                .inventoryId(inventoryId)
                .productId(productId)
                .inventoryQuantity(inventoryQuantity)
                .changeQuantity(changeQuantity)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}

