package com.example.mandarin_shop_back.controller.order;

import com.example.mandarin_shop_back.dto.order.request.AddOrderStatusReqDto;
import com.example.mandarin_shop_back.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/status")
    public ResponseEntity<?> addOrderStatus(@RequestBody AddOrderStatusReqDto addOrderStatusReqDto) {
        orderService.insertProductCategory(addOrderStatusReqDto);
        return ResponseEntity.created(null).body(true);
    }
}
