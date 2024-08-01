package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.inventory.Inventory;
import com.example.mandarin_shop_back.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    public int saveInventory(Inventory inventory);

    public List<Inventory> getInventory();
}
