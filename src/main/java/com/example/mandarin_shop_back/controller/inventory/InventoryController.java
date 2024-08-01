package com.example.mandarin_shop_back.controller.inventory;

import com.example.mandarin_shop_back.dto.inventory.request.InventoryReqDto;
import com.example.mandarin_shop_back.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/item")
    public ResponseEntity<?> registerInventory(@RequestBody InventoryReqDto inventoryReqDto) {
        return ResponseEntity.ok().body(inventoryService.saveInventory((inventoryReqDto)));
    }

    @GetMapping("/item")
    public ResponseEntity<?> getInventory() {
        return ResponseEntity.ok(inventoryService.getInventory());
    }
}
