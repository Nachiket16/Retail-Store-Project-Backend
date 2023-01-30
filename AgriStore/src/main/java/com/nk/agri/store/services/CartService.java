package com.nk.agri.store.services;

import com.nk.agri.store.dtos.AddItemToCartRequest;
import com.nk.agri.store.dtos.CartDto;

public interface CartService
{
    //add items to cart:
    //case1: cart for user is not available : we will create the cart and the cart item
    //case2: cart available add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItem);


    //remove all items from cart
    void clearCart(String userId);


    CartDto getCartByUser(String userId);

}
