package com.district12.backend.entities;

import com.district12.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private  String email;

    @Column(nullable = false)
    private  String fullName;

    @Column(nullable = false, length = 1200)
    @ToString.Exclude
    private  String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Address> addresses;

    public User(Long id){
        this.id = id;
    }

    public User(String email, String fullName, String password, Role role, String phoneNumber) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}
