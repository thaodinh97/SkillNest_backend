package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.OrderItemRequest;
import com.example.skillnest.dto.requests.OrderRequest;
import com.example.skillnest.dto.responses.OrderItemResponse;
import com.example.skillnest.dto.responses.OrderResponse;
import com.example.skillnest.entity.Order;
import com.example.skillnest.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Order toOrder(OrderRequest orderRequest);
    OrderResponse toOrderResponse(Order order);
    @Mapping(target = "courseId", source = "course.id")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    List<OrderItemResponse> toOrderItemResponses(List<OrderItem> items);
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemRequest orderItemRequest);
}
