package com.nk.agri.store.services.impl;

import com.nk.agri.store.dtos.AddItemToCartRequest;
import com.nk.agri.store.dtos.CartDto;
import com.nk.agri.store.entities.Cart;
import com.nk.agri.store.entities.CartItem;
import com.nk.agri.store.entities.Product;
import com.nk.agri.store.entities.User;
import com.nk.agri.store.exceptions.BadApiRequest;
import com.nk.agri.store.exceptions.ResourceNotFoundException;
import com.nk.agri.store.repositories.CartItemRepository;
import com.nk.agri.store.repositories.CartRepository;
import com.nk.agri.store.repositories.ProductRepository;
import com.nk.agri.store.repositories.UserRepository;
import com.nk.agri.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();
        //fetch product
        if (quantity <= 0) {
            throw new BadApiRequest("Requested quantity is not valid !!");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for the given ID"));

        //fetch the user from the DB
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user found for the given id "));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            //If cart is not available the new cart will be added
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        //perform cart operations
        //If cart items already present the update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        //create Items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found !!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        return modelMapper.map(cart, CartDto.class);
    }

}
