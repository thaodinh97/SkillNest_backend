package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.CartItemRequest;
import com.example.skillnest.dto.responses.CartItemResponse;
import com.example.skillnest.entity.Cart;
import com.example.skillnest.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseName")
    @Mapping(source = "course.price", target = "price")
    @Mapping(source = "course.instructor.fullName", target = "courseInstructor")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "addedAt", expression = "java(java.time.LocalDateTime.now())")
    CartItem toCartItem(CartItemRequest cartItemRequest);
    List<CartItemResponse> toCartItemResponses(List<CartItem> cartItems);
}
