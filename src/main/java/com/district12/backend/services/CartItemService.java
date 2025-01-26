package com.district12.backend.services;

import com.district12.backend.dtos.CartItemResponse;
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

}
