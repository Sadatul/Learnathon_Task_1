package com.district12.backend.repositories;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT new com.district12.backend.dtos.CartItemResponse(ci.id, p.id, p.name, p.description, ci.quantity) " +
            "FROM CartItem ci " +
            "JOIN ci.product p " +
            "WHERE ci.user.id = :userId")
    List<CartItemResponse> findCartItemsByUserId(@Param("userId") Long userId);

    Optional<CartItem> findByUserIdAndProduct(Long userId, Product product);

    @Query("SELECT new com.district12.backend.dtos.CartItemResponse(ci.id, p.id, p.name, p.description, ci.quantity) " +
            "FROM CartItem ci " +
            "JOIN ci.product p " +
            "WHERE ci.order.id = :orderId")
    List<CartItemResponse> findCartItemsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT COUNT(ci) = :listSize " +
            "FROM CartItem ci " +
            "WHERE ci.id IN :cartItemIds AND ci.user.id = :userId")
    boolean doAllCartItemsBelongToUser(@Param("listSize") int listSize,
                                       @Param("cartItemIds") List<Long> cartItemIds,
                                       @Param("userId") Long userId);

    @Query("SELECT COUNT(ci) > 0 " +
            "FROM CartItem ci " +
            "WHERE ci.id IN :cartItemIds AND ci.order IS NOT NULL")
    boolean isAnyCartItemInAnotherOrder(@Param("cartItemIds") List<Long> cartItemIds);


    @Modifying
    @Query("UPDATE CartItem ci SET ci.order = :newOrder WHERE ci.id IN :cartItemIds")
    void updateOrderForCartItems(@Param("newOrder") Order newOrder, @Param("cartItemIds") List<Long> cartItemIds);

}
