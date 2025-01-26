package com.district12.backend.repositories;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT new com.district12.backend.dtos.CartItemResponse(p.name, p.description, ci.quantity) " +
            "FROM CartItem ci " +
            "JOIN ci.product p " +
            "WHERE ci.user.id = :userId")
    List<CartItemResponse> findCartItemsByUserId(@Param("userId") Long userId);

    Optional<CartItem> findByUserIdAndProduct(Long userId, Product product);

    @Query("SELECT new com.district12.backend.dtos.CartItemResponse(p.name, p.description, ci.quantity) " +
            "FROM CartItem ci " +
            "JOIN ci.product p " +
            "WHERE ci.order.id = :orderId")
    List<CartItemResponse> findCartItemsByOrderId(@Param("orderId") Long orderId);

}
