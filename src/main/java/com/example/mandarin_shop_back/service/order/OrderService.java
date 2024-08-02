package com.example.mandarin_shop_back.service.order;

import com.example.mandarin_shop_back.dto.order.request.AddOrderStatusReqDto;
import com.example.mandarin_shop_back.dto.order.request.OrderItemReqDto;
import com.example.mandarin_shop_back.dto.order.request.OrderReqDto;
import com.example.mandarin_shop_back.dto.order.response.OrderItemRespDto;
import com.example.mandarin_shop_back.dto.order.response.OrderRespDto;
import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.entity.order.OrderItem;
import com.example.mandarin_shop_back.exception.DeleteException;
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

    @Transactional(readOnly = true)
    public List<OrderRespDto> searchOrders() {
        List<Order> orders = orderMapper.findOrders();
        System.out.println("Number of orders: " + orders.size()); // 디버깅을 위해 추가

        List<OrderRespDto> orderRespDtos = new ArrayList<>();
        for (Order order : orders) {
            orderRespDtos.add(order.toOrderRespDto());
        }

        return orderRespDtos;
    }

    @Transactional(readOnly = true)
    public List<OrderRespDto> searchOrdersByUserId(int userId) {
        List<Order> orders = orderMapper.findOrdersByUserId(userId);
        System.out.println("Number of orders: " + orders.size()); // 디버깅을 위해 추가

        List<OrderRespDto> orderRespDtos = new ArrayList<>();
        for (Order order : orders) {
            orderRespDtos.add(order.toOrderRespDto());
        }
        return orderRespDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertOrderItem(OrderItemReqDto orderItemReqDto) {

        OrderItem orderItem = orderItemReqDto.toEntity();
        int successCount = orderMapper.saveOrderItem(orderItem);

        if(successCount < 1) {
            throw new SaveException();
        }
    }

    public List<OrderItemRespDto> searchOrderItem () {
        List<OrderItem> orderitems = orderMapper.findOrderItem();
        System.out.println("Order items: " + orderitems); // 로그 추가

        List<OrderItemRespDto> orderItemRespDtos = new ArrayList<>();
        for (OrderItem orderItem : orderitems) {
            orderItemRespDtos.add(orderItem.toOrderItemRespDto());
        }

        return orderItemRespDtos;
    }

    public List<OrderItemRespDto> searchOrderItemByUserId (int userId) {
        List<OrderItem> orderitems = orderMapper.findOrderItemByUserId(userId);

        List<OrderItemRespDto> orderItemRespDtos = new ArrayList<>();
        for (OrderItem orderItem : orderitems) {
            orderItemRespDtos.add(orderItem.toOrderItemRespDto());
        }

        return orderItemRespDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(int orderId) {
        int successCount = 0;

        // 주문 상태를 취소로 변경 (order_status_id = 2)
        successCount += orderMapper.cancelOrder(orderId);

        if (successCount < 1) {
            throw new DeleteException(Map.of("cancelOrder 오류", "정상적으로 주문이 취소되지 않았습니다."));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderItem(int orderId) {
        int successCount = 0;

        // 주문 상태가 이미 취소된 주문의 항목만 삭제
        successCount += orderMapper.deleteOrderItem(orderId);

        if (successCount < 1) {
            throw new DeleteException(Map.of("deleteOrderItem 오류", "정상적으로 주문 항목이 삭제되지 않았습니다."));
        }
    }


}
