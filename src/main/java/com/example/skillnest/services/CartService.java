package com.example.skillnest.services;

import com.example.skillnest.dto.requests.CartItemRequest;
import com.example.skillnest.dto.requests.CartRequest;
import com.example.skillnest.dto.responses.CartItemResponse;
import com.example.skillnest.dto.responses.CartResponse;
import com.example.skillnest.entity.Cart;
import com.example.skillnest.entity.CartItem;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.CartItemMapper;
import com.example.skillnest.mapper.CartMapper;
import com.example.skillnest.repositories.CartItemRepository;
import com.example.skillnest.repositories.CartRepository;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;
    CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public CartResponse addToCart(CartItemRequest cartItemRequest) {
        var context =  SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        UUID userUuid = UUID.fromString(userId);

        Cart cart = cartRepository.findByUserId(userUuid)
                .orElseGet(() -> createNewCart(userUuid));

        Course course = courseRepository.findById(UUID.fromString(cartItemRequest.getCourseId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        boolean exists = cart.getCartItems().stream()
                .anyMatch(item -> item.getCourse().getId().equals(course.getId()));

        if(!exists) {
            CartItem cartItem = cartItemMapper.toCartItem(cartItemRequest);
            cartItem.setCourse(course);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartResponse(savedCart);
    }

    public CartResponse getCartByUserId() {
        var context =  SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        UUID userUuid = UUID.fromString(userId);
        Cart cart = cartRepository.findByUserId(userUuid)
                .orElseGet(() -> createNewCart(userUuid));
        return cartMapper.toCartResponse(cart);
    }

    public void deleteCartItem(String cartItemId) {
        CartItem cartItem = cartItemRepository.findById(UUID.fromString(cartItemId))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        cartItemRepository.delete(cartItem);
    }

    private Cart createNewCart(UUID userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
}
