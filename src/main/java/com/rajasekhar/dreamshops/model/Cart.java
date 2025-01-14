package com.rajasekhar.dreamshops.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private BigDecimal totalAmout=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public void addItem(CartItem cartItem) {
    }

    public void removeItem(CartItem itemToRemove) {
    }
}
