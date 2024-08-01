package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.inventory.Inventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryMapper {
    public int saveInventory(Inventory inventory);
}
