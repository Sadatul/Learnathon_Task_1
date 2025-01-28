package com.district12.backend.services.impls;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.CartItemRepository;
import com.district12.backend.services.abstractions.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemResponse> getCartItemsForUser(Long userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }

    @Override
    public CartItemResponse addCartItem(User user, Product product, Integer quantity) {
        CartItem savedCartItem = cartItemRepository.findByUserIdAndProduct(user.getId(), product)
                .map(existingCartItem -> increaseCartItemQuantity(existingCartItem, quantity))
                .orElseGet(() -> createAndSaveCartItem(user, product, quantity));

        return new CartItemResponse(
                savedCartItem.getId(), savedCartItem.getProduct().getId(),
                savedCartItem.getProduct().getName(),
                savedCartItem.getProduct().getDescription(), savedCartItem.getQuantity()
        );
    }

    private CartItem createAndSaveCartItem(User user, Product product, Integer quantity) {
        CartItem newCartItem = new CartItem(user, product, quantity);
        return cartItemRepository.save(newCartItem);
    }

    private CartItem increaseCartItemQuantity(CartItem cartItem, Integer quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        String errorMessage = "User is not authorized to update this cart item";
        CartItem existingCartItem = verifyCartItemAndUserId(userId, cartItemId, errorMessage);

        existingCartItem.setQuantity(quantity);
        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);

        return new CartItemResponse(
                updatedCartItem.getId(), updatedCartItem.getProduct().getId(),
                updatedCartItem.getProduct().getName(),
                updatedCartItem.getProduct().getDescription(), updatedCartItem.getQuantity()
        );
    }

    @Override
    public void deleteCartItem(Long userId, Long cartItemId) {
        String errorMessage = "User is not authorized to delete this cart item";
        CartItem existingCartItem = verifyCartItemAndUserId(userId, cartItemId, errorMessage);

        cartItemRepository.delete(existingCartItem);
    }

    @Override
    public List<CartItemResponse> getCartItemsByOrderId(Long orderId) {
        return cartItemRepository.findCartItemsByOrderId(orderId);
    }

    @Override
    public void doAllCartItemsBelongToUser(List<Long> cartItemIds, Long userId) {
        boolean allBelongToUser = cartItemRepository.doAllCartItemsBelongToUser(
                cartItemIds.size(), cartItemIds, userId);
        if (!allBelongToUser)
            throw new IllegalArgumentException("One or more cart item(s) do not belong to the user associated with the order.");
    }

    @Override
    public void isAnyCartItemInAnotherOrder(List<Long> cartItemIds) {
        boolean isInAnotherOrder = cartItemRepository.isAnyCartItemInAnotherOrder(cartItemIds);
        if (isInAnotherOrder)
            throw new IllegalArgumentException("One or more cart item(s) is already in another order.");
    }

    @Override
    @Transactional
    public void updateCartItemsOrderId(List<Long> cartItemIds, Order newOrder) {
        cartItemRepository.updateOrderForCartItems(newOrder, cartItemIds);
    }

    private CartItem verifyCartItemAndUserId(Long userId, Long orderId, String errorMessage) {
        CartItem existingCartItem = cartItemRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found for the given id")
                );

        if (!existingCartItem.getUser().getId().equals(userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);

        return existingCartItem;
    }
}
