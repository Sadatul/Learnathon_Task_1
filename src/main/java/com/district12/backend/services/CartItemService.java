package com.district12.backend.services;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItemResponse> getCartItemsForUser(Long userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }

    public CartItem addCartItem(User user, Product product, Integer quantity) {
        return cartItemRepository.findByUserIdAndProduct(user.getId(), product)
                .map(existingCartItem -> increaseCartItemQuantity(existingCartItem, quantity))
                .orElseGet(() -> createAndSaveCartItem(user, product, quantity));
    }

    private CartItem createAndSaveCartItem(User user, Product product, Integer quantity) {
        CartItem newCartItem = new CartItem(user, product, quantity);
        return cartItemRepository.save(newCartItem);
    }

    private CartItem increaseCartItemQuantity(CartItem cartItem, Integer quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItemQuantity(Long userId, Product product, Integer quantity) {
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProduct(userId, product)
                .orElseThrow(() -> new RuntimeException("CartItem not found for the given user and product"));
        existingCartItem.setQuantity(quantity);
        return cartItemRepository.save(existingCartItem);
    }

    public void deleteCartItem(Long userId, Product product) {
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProduct(userId, product)
                .orElseThrow(() -> new RuntimeException("CartItem not found for the given user and product"));
        cartItemRepository.delete(existingCartItem);
    }

    public List<CartItemResponse> getCartItemsByOrderId(Long orderId) {
        return cartItemRepository.findCartItemsByOrderId(orderId);
    }

    public void updateCartItemsOrderId(List<Long> cartItemIds, Order newOrder) {
        cartItemRepository.updateOrderForCartItems(newOrder, cartItemIds);
    }

}
