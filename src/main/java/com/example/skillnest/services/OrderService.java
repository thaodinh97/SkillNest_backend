package com.example.skillnest.services;

import com.example.skillnest.dto.requests.OrderItemRequest;
import com.example.skillnest.dto.requests.OrderRequest;
import com.example.skillnest.dto.responses.OrderResponse;
import com.example.skillnest.entity.*;
import com.example.skillnest.enums.OrderStatus;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.OrderMapper;
import com.example.skillnest.repositories.CartRepository;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.OrderRepository;
import com.example.skillnest.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    UserRepository  userRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    CartRepository cartRepository;

    public OrderResponse checkout() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        UUID userUuid = UUID.fromString(userId);
        Cart cart = cartRepository.findByUserId(userUuid)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.pending.name());

        List<OrderItem> orderItems = new ArrayList<>();
        Double total = 0.0;
        for (CartItem cartItem : cart.getCartItems()) {
            Course course = cartItem.getCourse();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setCourse(course);
            orderItem.setPrice(course.getPrice());
            orderItems.add(orderItem);
            total += orderItem.getPrice();
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);
        Order savedOrder = orderRepository.save(order);
        cartRepository.delete(cart);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders = orderRepository.findAll();
        return orders;
    }

    public List<OrderResponse> getOrdersByUserId() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        UUID userUuid = UUID.fromString(userId);
        List<Order> orders = orderRepository.findByUserId(userUuid);
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();

    }
}
