package com.example.mandarin_shop_back.service.order;

import com.example.mandarin_shop_back.dto.order.request.AddOrderStatusReqDto;
import com.example.mandarin_shop_back.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public void insertProductCategory(AddOrderStatusReqDto addOrderStatusReqDto) {
        orderMapper.saveOrderStatus(addOrderStatusReqDto.toEntity());
    }
}
