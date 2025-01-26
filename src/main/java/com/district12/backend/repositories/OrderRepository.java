package com.district12.backend.repositories;

import com.district12.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findOrdersByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status = 'COMPLETED' OR o.status = 'CANCELLED')")
    List<Order> findPastOrdersByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Order o SET o.status = 'CANCELLED' WHERE o.id = :orderId AND (o.status = 'CHECKED_OUT' OR o.status = 'CONFIRMED')")
    void cancelOrderById(@Param("orderId") Long orderId);

    @Modifying
    @Query("UPDATE Order o SET o.status = 'CONFIRMED' WHERE o.id = :orderId AND (o.status = 'CHECKED_OUT')")
    void confirmOrderById(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE (o.status = 'CONFIRMED')")
    List<Order> getAllReadyOrders();

    @Modifying
    @Query("UPDATE Order o SET o.status = 'SHIPPED' WHERE (o.status = 'CONFIRMED')")
    List<Order> shipAllReadyOrders();

    @Modifying
    @Query("UPDATE Order o SET o.status = 'SHIPPED' WHERE o.id = :orderId AND (o.status = 'CONFIRMED')")
    Order shipOneReadyOrderForAdmin(@Param("orderId") Long orderId);

    @Modifying
    @Query("UPDATE Order o SET o.status = 'COMPLETED' WHERE o.id = :orderId")
    Order completeOrder(@Param("orderId") Long orderId);

}