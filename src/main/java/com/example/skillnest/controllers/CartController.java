package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.CartItemRequest;
import com.example.skillnest.dto.requests.CartRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.CartResponse;
import com.example.skillnest.services.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @GetMapping
    ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .code(1000)
                .result(cartService.getCartByUserId())
                .build();
    }

    @PostMapping("/item")
    ApiResponse<CartResponse> addToCart(@RequestBody CartItemRequest request) {
        return ApiResponse.<CartResponse>builder()
                .code(1000)
                .result(cartService.addToCart(request))
                .build();
    }

    @DeleteMapping("/item/{itemId}")
    ApiResponse<String> removeFromCart(@PathVariable String itemId) {
        ApiResponse<String> response =  new ApiResponse<>();
        try {
            cartService.deleteCartItem(itemId);
            response.setResult("Item has been deleted successfully");
        } catch (Exception e) {
            response.setResult("error " + e.getMessage());
        }
        return response;
    }
}
