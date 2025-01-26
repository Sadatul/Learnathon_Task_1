package com.district12.backend.services;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.CartItemRepository;
import com.district12.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public List<CartItemResponse> getCartItemsForUser(Long userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }

    public CartItem addCartItem(Long userId, Product product, Integer quantity) {
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserIdAndProduct(userId, product);
        if (existingCartItem.isPresent()) {
            existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + quantity);
            return cartItemRepository.save(existingCartItem.get());
        }
        else {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for the given user id"));
            CartItem newCartItem = new CartItem(existingUser, product, quantity);
            return cartItemRepository.save(newCartItem);
        }
    }

}
