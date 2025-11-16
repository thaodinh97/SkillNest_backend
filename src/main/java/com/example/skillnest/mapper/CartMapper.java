package com.example.skillnest.mapper;

import com.example.skillnest.dto.responses.CartResponse;
import com.example.skillnest.entity.Cart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "items")
    CartResponse toCartResponse(Cart cart);

    @AfterMapping
    default void calculateTotalPrice(Cart cart, @MappingTarget CartResponse.CartResponseBuilder cartResponse)
    {
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            cartResponse.totalPrice(0.0);
            return;
        }

        Double totalPrice = cart.getCartItems().stream()
                .map(item -> item.getCourse().getPrice())
                .reduce(0.0, Double::sum);
        cartResponse.totalPrice(totalPrice);
    }
}
