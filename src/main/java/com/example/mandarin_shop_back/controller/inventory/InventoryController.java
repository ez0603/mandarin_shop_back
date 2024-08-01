package com.example.mandarin_shop_back.controller.inventory;

import com.example.mandarin_shop_back.dto.inventory.request.InventoryReqDto;
import com.example.mandarin_shop_back.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<?> registerInventory(@RequestBody InventoryReqDto inventoryReqDto) {
        return ResponseEntity.ok().body(inventoryService.saveInventory((inventoryReqDto)));
    }
}
