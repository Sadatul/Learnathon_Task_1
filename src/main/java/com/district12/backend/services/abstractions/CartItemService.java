package com.district12.backend.services.abstractions;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;

import java.util.List;

public interface CartItemService {

    List<CartItemResponse> getCartItemsForUser(Long userId);
    CartItemResponse addCartItem(
            User user, Product product, Integer quantity);
    CartItemResponse updateCartItemQuantity(
            Long userId, Long cartItemId, Integer quantity);
    void deleteCartItem(Long userId, Long cartItemId);
    List<CartItemResponse> getCartItemsByOrderId(Long orderId);
    void doAllCartItemsBelongToUser(
            List<Long> cartItemIds, Long userId);
    void isAnyCartItemInAnotherOrder(List<Long> cartItemIds);
    void updateCartItemsOrderId(List<Long> cartItemIds, Order newOrder);

}
