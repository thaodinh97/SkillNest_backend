package com.example.skillnest.services;

import com.example.skillnest.dto.requests.OrderItemRequest;
import com.example.skillnest.dto.requests.OrderRequest;
import com.example.skillnest.dto.responses.OrderResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Order;
import com.example.skillnest.entity.OrderItem;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.OrderMapper;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.OrderRepository;
import com.example.skillnest.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    UserRepository  userRepository;
    OrderRepository orderRepository;
    CourseRepository courseRepository;
    OrderMapper orderMapper;
    @Transactional
    public OrderResponse getOrCreateCart() {
        var context =  SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        UUID id = UUID.fromString(userId);
        Order cart = orderRepository.findByUserIdAndStatus(id, "pending")
                .orElseGet(() -> {
                    User user = userRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                    List<OrderItem> orderItems = new ArrayList<>();
                    OrderRequest orderRequest = OrderRequest.builder()
                            .orderItems(orderItems)
                            .build();
                    Order newCart = orderMapper.toOrder(orderRequest);
                    newCart.setUser(user);
                    newCart.setStatus("pending");
                    newCart.setTotalPrice(0.00);
                    return orderRepository.save(newCart);
                });
        return orderMapper.toOrderResponse(cart);
    }

    public OrderResponse addItem(UUID orderId, OrderItemRequest orderItemRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        var context =  SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();
        UUID userId = UUID.fromString(id);
        if(!order.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        Course course = courseRepository.findById(UUID.fromString(orderItemRequest.getCourseId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        boolean exists = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getCourse().getId().equals(course.getId()));
        if(exists) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        OrderItem orderItem = orderMapper.toOrderItem(orderItemRequest);
        orderItem.setCourse(course);
        orderItem.setOrder(order);
        order.getOrderItems().add(orderItem);
        order.setTotalPrice(order.getTotalPrice() +  orderItem.getPrice());
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }
}
