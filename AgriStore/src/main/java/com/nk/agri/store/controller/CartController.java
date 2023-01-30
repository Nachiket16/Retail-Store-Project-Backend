package com.nk.agri.store.controller;

import com.nk.agri.store.dtos.AddItemToCartRequest;
import com.nk.agri.store.dtos.ApiResponseMsg;
import com.nk.agri.store.dtos.CartDto;
import com.nk.agri.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMsg> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMsg response = ApiResponseMsg.builder()
                .msg("Item is removed !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMsg> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMsg response = ApiResponseMsg.builder()
                .msg("Now cart is blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //add items to cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
