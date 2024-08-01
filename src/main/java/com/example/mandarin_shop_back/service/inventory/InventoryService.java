package com.example.mandarin_shop_back.service.inventory;

import com.example.mandarin_shop_back.dto.inventory.request.InventoryReqDto;
import com.example.mandarin_shop_back.entity.inventory.Inventory;
import com.example.mandarin_shop_back.repository.InventoryMapper;
import com.example.mandarin_shop_back.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Transactional(rollbackFor = Exception.class)
    public int saveInventory(InventoryReqDto inventoryReqDto) {
        Inventory inventoryEntity = inventoryReqDto.toEntity();
        validateInventory(inventoryEntity);
        return inventoryMapper.saveInventory(inventoryEntity);
    }

    private void validateInventory(Inventory inventory) {
        // 필수 필드 체크, 재고 수량이 음수인지 확인하는 등의 데이터 검증 로직 추가
        if (inventory.getInventoryQuantity() < 0) {
            throw new IllegalArgumentException("Inventory quantity cannot be negative");
        }
        System.out.println("확인");
        // 추가 검증 로직이 필요하다면 여기에 추가
    }

}
