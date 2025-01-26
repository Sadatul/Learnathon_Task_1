package com.district12.backend.entities;

import com.district12.backend.enums.OrderStatus;
import com.district12.backend.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Order(User user, OrderStatus status, PaymentMethod paymentMethod) {
        this.user = user;
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
