package com.example.mandarin_shop_back.service.order;

import com.example.mandarin_shop_back.dto.order.request.AddOrderStatusReqDto;
import com.example.mandarin_shop_back.dto.order.request.OrderReqDto;
import com.example.mandarin_shop_back.dto.order.response.OrderRespDto;
import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.exception.SaveException;
import com.example.mandarin_shop_back.repository.OrderMapper;
import com.example.mandarin_shop_back.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;

    public void insertProductCategory(AddOrderStatusReqDto addOrderStatusReqDto) {
        orderMapper.saveOrderStatus(addOrderStatusReqDto.toEntity());
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertOrder(OrderReqDto orderReqDto) {
        int userId = orderReqDto.getUserId();

        // userId가 존재하는지 확인
        if (!userService.userExists(userId)) {
            throw new IllegalArgumentException("Invalid userId: " + userId);
        }

        Order order = orderReqDto.toEntity();
        int successCount = orderMapper.saveOrder(order);

        if(successCount < 1) {
            throw new SaveException();
        }
    }

    public List<OrderRespDto> searchOrders () {
        List<Order> orders = orderMapper.findOrders();
        List<OrderRespDto> orderRespDtos = new ArrayList<>();
        for (Order order : orders) {
            orderRespDtos.add(order.toOrderRespDto());
        }

        return orderRespDtos;
    }
}
