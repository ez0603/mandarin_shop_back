package com.example.mandarin_shop_back.controller.order;

import com.example.mandarin_shop_back.aop.annotation.ParamsPrintAspect;
import com.example.mandarin_shop_back.dto.order.request.AddOrderStatusReqDto;
import com.example.mandarin_shop_back.dto.order.request.OrderReqDto;
import com.example.mandarin_shop_back.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/orders")
    public ResponseEntity<?> addOrder(@RequestBody OrderReqDto orderReqDto) {
        orderService.insertOrder(orderReqDto);
        return ResponseEntity.created(null).body(true);
    }

    @ParamsPrintAspect
    @GetMapping("/orders")
    public ResponseEntity<?> getOrder() {
        return ResponseEntity.ok(orderService.searchOrders());
    }

}
